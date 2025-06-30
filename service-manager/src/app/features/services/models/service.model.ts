export interface Owner {
    id: string;
    name: string;
    accountNumber: string;
    level: number;
  }
  
  export interface Resource {
    id: string;
    owners: Owner[];
  }
  
  export interface ServiceModel {
    id: string;
    resources: Resource[];
  }
  