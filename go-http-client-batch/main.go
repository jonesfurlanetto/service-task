package main

import (
	"flag"
	"fmt"
	"net/http"
	"sync"
	"time"
)

func sendRequest(client *http.Client, url string, id int) {
	resp, err := client.Get(url)
	if err != nil {
		fmt.Printf("Request %d failed: %v\n", id, err)
		return
	}
	defer resp.Body.Close()
	fmt.Printf("Request %d completed with status: %s\n", id, resp.Status)
}

func main() {
	var parallelRequests int
	var steps int
	var url string

	flag.IntVar(&parallelRequests, "parallel", 100, "Number of parallel requests per step")
	flag.IntVar(&steps, "steps", 10, "Number of steps (batches)")
	flag.StringVar(&url, "url", "http://localhost:8080/api/services/all", "Target URL to send requests to")
	flag.Parse()

	client := &http.Client{
		Timeout: 10 * time.Second,
	}

	for step := 1; step <= steps; step++ {
		fmt.Printf("\n--- Step %d ---\n", step)
		var wg sync.WaitGroup

		for i := 0; i < parallelRequests; i++ {
			wg.Add(1)
			go func(id int) {
				defer wg.Done()
				sendRequest(client, url, id)
			}(i + (step-1)*parallelRequests)
		}

		wg.Wait()
	}
}
