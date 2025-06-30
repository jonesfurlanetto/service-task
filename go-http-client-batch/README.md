# Go HTTP Batch Client

This is a high-performance HTTP client written in Go that sends requests in **configurable parallel batches**. It's
useful for load testing APIs, benchmarking, or simulating concurrent traffic to an endpoint.

---

## Features

- Sends HTTP requests in parallel.
- Executes requests in batch steps.
- Configurable number of parallel requests and number of steps.
- Uses efficient concurrency with goroutines and WaitGroups.
- Reuses a single HTTP client with timeout.

---

## Requirements

- [Go 1.20+](https://go.dev/dl) installed and available in your system

---

## How to Run

- Run the application:

```bash
go run main.go -parallel=100 -steps=10 -url=http://localhost:8080/api/services/all
```

---

## Command-line Flags

| Flag        | Description                                  | Default                                  |
|-------------|----------------------------------------------|------------------------------------------|
| `-parallel` | Number of parallel requests per step (batch) | `100`                                    |
| `-steps`    | Number of steps (batches)                    | `10`                                     |
| `-url`      | Target URL for sending requests              | `http://localhost:8080/api/services/all` |

### Example

```bash
go run main.go -parallel=50 -steps=5 -url=http://localhost:8080/api/services/all
```

This will send to a localhost get all services. So if you want to use this endpoint, make sure the project [`services`](../services) is running:

- 5 steps
- 50 parallel requests per step
- Total of 250 requests to `http://localhost:8080/api/services/all`

---

## Technologies Used

- **GoLang**
- `net/http`: standard HTTP client package in Go.
- `sync.WaitGroup`: concurrency control mechanism.
- `flag`: for command-line flag parsing.
