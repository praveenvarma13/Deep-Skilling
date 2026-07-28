## Cloud Fundamentals 
## ☁️ Module: Cloud Computing Architecture & Service Models

Cloud computing has reshaped how modern enterprise software is built, hosted, and scaled. By shifting away from physical infrastructure management toward on-demand resource provisioning over the internet, organizations achieve unprecedented agility, cost efficiency, and global scalability.

---

### 1. Fundamentals & Evolution of Infrastructure

#### 🏢 Traditional IT Deployment vs. Cloud Computing
*   **Traditional IT Deployment:** Relies on physical hardware, dedicated data centers, and static server provisioning. Organizations must buy, rack, configure, and maintain physical servers long before deploying applications. Capital expenditure (CapEx) is high, provisioning takes weeks or months, and scaling requires physical hardware upgrades.
*   **Cloud Computing:** The on-demand delivery of computing services—including servers, storage, databases, networking, and software—over the internet with **pay-as-you-go pricing**. Resources can be provisioned or terminated programmatically in seconds, shifting costs to an operational expenditure (OpEx) model.

#### 🖥️ Virtualization Concepts
**Virtualization** is the foundational technology that makes cloud computing possible. It uses software called a **Hypervisor** to abstract physical hardware resources (CPU, RAM, storage, network) into multiple isolated virtual environments known as **Virtual Machines (VMs)**.

*   **Type-1 Hypervisor (Bare-Metal):** Runs directly on the physical host hardware without an underlying host OS (e.g., VMware ESXi, KVM, Microsoft Hyper-V). It provides maximum performance and security, making it the bedrock of cloud provider infrastructure.
*   **Type-2 Hypervisor (Hosted):** Runs on top of a conventional host operating system (e.g., Oracle VirtualBox, VMware Workstation). Ideal for local testing and developer workstations.

#### 🧩 Service-Oriented Architecture (SOA)
**Service-Oriented Architecture (SOA)** is an architectural pattern where software components deliver services to other components over a network using decoupled communication protocols. SOA laid the conceptual foundation for modern cloud services and microservices by emphasizing:
*   Loose coupling between infrastructure and software layers.
*   Reusable, network-accessible web interfaces and APIs.
*   Interoperability across heterogeneous technology stacks.

---

### 2. Cloud vs. On-Premises Architecture

#### 🏢 On-Premises
*   **Pros:** Complete physical ownership and control of hardware, total data sovereignty, predictable performance for specialized legacy workloads, zero dependence on third-party cloud vendor availability.
*   **Cons:** High upfront capital investment (CapEx), high ongoing operational maintenance (power, cooling, physical security, sysadmin labor), slow hardware procurement cycles, capacity planning risks (over-provisioning or under-provisioning).

#### ☁️ Cloud Computing
*   **Pros:** Rapid self-service provisioning, elastic scaling (up or down based on real-time traffic), geographic distribution across global regions, shift from upfront CapEx to pay-as-you-go OpEx, outsourced hardware maintenance and security patching.
*   **Cons:** Ongoing operational costs if unmanaged, potential vendor lock-in, shared-tenancy resource noisy neighbors, strict compliance/regulatory hurdles for sensitive data storage in third-party data centers.

---

### 3. Cloud Service Models: IaaS, PaaS, SaaS

Cloud services are categorized into three primary delivery models, each offering a different balance of control, flexibility, and management overhead.

```text
 ┌────────────────────────────────────────────────────────────────────────┐
 │                      MANAGEMENT RESPONSIBILITY                         │
 ├──────────────────┬──────────────────┬──────────────────┬───────────────┤
 │  On-Premises     │     IaaS         │     PaaS         │     SaaS      │
 ├──────────────────┼──────────────────┼──────────────────┼───────────────┤
 │ Applications     │ Applications     │ Applications     │ Managed by    │
 │ Data             │ Data             │ Data             │ Vendor        │
 │ Runtime          │ Runtime          │ Managed by       │               │
 │ Middleware       │ Middleware       │ Vendor           │               │
 │ OS               │ OS               │                  │               │
 ├──────────────────┼──────────────────┴──────────────────┴───────────────┤
 │ Virtualization   │                                                     │
 │ Servers          │              Managed by Cloud Vendor                │
 │ Storage          │                                                     │
 │ Networking       │                                                     │
 └──────────────────┴─────────────────────────────────────────────────────┘
```

🏗️ 1. Infrastructure as a Service (IaaS)
* Overview: Provides virtualized computing resources over the internet. The vendor manages physical data centers, host servers, storage arrays, and network hardware, while you manage the OS, middleware, runtime environments, application code, and data.

* Key Controls: Maximum flexibility and control over OS configurations, networking rules, and security firewall settings.

* Industry Examples: Amazon EC2, Azure Virtual Machines, Google Compute Engine (GCE).

🛠️ 2. Platform as a Service (PaaS)
* Overview: Provides a fully managed platform environment for developers to build, test, deploy, and run applications without worrying about underlying operating systems, server maintenance, or runtime patching.

* Key Controls: You focus entirely on application code and database schemas; the cloud vendor handles OS updates, runtime configurations, scaling, and load balancing.

* Industry Examples: AWS Elastic Beanstalk, Heroku, Google App Engine, Azure App Services.

📱 3. Software as a Service (SaaS)
* Overview: Delivers fully functional, end-user applications hosted entirely in the cloud and accessible via web browsers or mobile clients.

* Key Controls: You manage user access settings and configuration parameters; the vendor manages everything else, including infrastructure, code updates, security, and scalability.

* Industry Examples: Microsoft 365, Salesforce, Google Workspace, Jira Cloud, Zoom.

4. Cloud Deployment Models
Cloud environments are deployed in four primary operational models depending on business needs, regulatory constraints, and security requirements:

* 🌐 Public Cloud: Infrastructure owned and managed by a multi-tenant cloud provider (e.g., AWS, Azure) and shared across multiple organizations over the public internet. Offers maximum scalability and cost efficiency.

* 🔒 Private Cloud: Infrastructure dedicated exclusively to a single organization. It can be hosted on-premises in a company-owned data center or managed by a third-party vendor off-site. Delivers strict data control and compliance.

* 🔀 Hybrid Cloud: Connects public cloud resources with private clouds or on-premises data centers using encrypted network tunnels or direct fiber lines (e.g., AWS Direct Connect). Allows companies to keep sensitive data on-premises while scaling web applications in the public cloud.

* 👥 Community Cloud: Shared infrastructure built specifically for a group of organizations with common compliance, security, or regulatory requirements (e.g., U.S. government agencies, healthcare conglomerates).

5. Cloud Service Providers Overview (The Big Three)
The global cloud market is dominated by three major hyperscale providers, each offering a vast ecosystem of cloud-native primitives:

* 🟧 Amazon Web Services (AWS): The pioneer and market leader in cloud computing. Known for its vast depth of specialized services, global availability zones, strong developer ecosystem, and cloud-native toolsets.

* 🟦 Microsoft Azure: The top choice for enterprises deeply integrated with Microsoft enterprise tools (Windows Server, Active Directory, Office 365). Excels in hybrid cloud deployments, enterprise integration, and enterprise AI offerings.

* 🟥 Google Cloud Platform (GCP): Renowned for high-performance computing, advanced data analytics, machine learning/AI tooling, open-source innovations (originating Kubernetes), and global networking infrastructure.
---

## 🖥️ Module: Amazon EC2 – Instances, AMIs, Security Groups & Key Pairs

**Amazon Elastic Compute Cloud (Amazon EC2)** is the central compute primitive of Amazon Web Services (AWS). It provides secure, resizable compute capacity in the cloud, allowing engineers to launch virtual servers on demand without investing in physical hardware upfront.

---

### 1. What is Amazon EC2?

Amazon EC2 abstracts physical server hardware into **EC2 Instances**—virtual compute nodes running on AWS hypervisors. 

#### Key Attributes of EC2:
* **Elasticity:** Scale virtual compute capacity up or down dynamically in response to web traffic spikes or processing requirements.
* **Complete Administrative Control:** Full root (Linux) or Administrator (Windows) access to the underlying virtual operating system.
* **Pay-as-You-Go Pricing:** Pay only for the compute time, storage, and networking resources your instances actively consume.

---

### 2. Amazon Machine Images (AMIs)

An **Amazon Machine Image (AMI)** is a pre-configured template that contains the information required to launch an EC2 instance.

#### 📦 What an AMI Contains:
* A **Root Volume Template:** The operating system (e.g., Amazon Linux 2023, Ubuntu 22.04 LTS, Windows Server), initial software packages, and configurations.
* **Launch Permissions:** Controls which AWS accounts are authorized to use the AMI (Public, Private, or AWS Marketplace).
* **Block Device Mapping:** Specifies the volume storage parameters (Amazon EBS or Instance Store) attached to the instance upon boot.

#### 🛠️ AMI Sourcing Options:
* **AWS Quickstart AMIs:** Official, pre-packaged OS templates provided directly by AWS.
* **AWS Marketplace AMIs:** Specialized third-party software stacks (e.g., pre-configured WordPress, Cisco Routers, or Machine Learning frameworks).
* **Custom AMIs:** Custom images created from your own configured EC2 instances to allow rapid, identical auto-scaling.

---

### 3. EC2 Instance Naming & Types

AWS organizes EC2 instances into distinct families optimized for different compute workloads.

#### 🔠 Instance Naming Convention (e.g., `t3.micro`, `m5.xlarge`)
* **First Letter (`t`, `m`, `c`):** Instance Family (e.g., `t` = General Purpose Burstable, `c` = Compute Optimized).
* **Number (`3`, `5`):** Generation indicator (newer generations offer better performance per dollar).
* **Size (`micro`, `medium`, `xlarge`):** Denotes resource scale (vCPUs, RAM capacity, and network bandwidth).

#### 🏷️ Common Instance Families:
* **General Purpose (`t3`, `m6g`):** Balanced compute, memory, and networking resources. Ideal for web servers, code repositories, and development environments.
* **Compute Optimized (`c6i`):** High-performance processors for compute-heavy workloads (batch processing, media encoding, gaming servers).
* **Memory Optimized (`r6i`):** Fast memory performance for high-throughput databases (PostgreSQL, Redis, in-memory analytics).
* **Free Tier Option (`t2.micro` / `t3.micro`):** Provides 1 vCPU and 1 GiB RAM, covered under the AWS Free Tier allowance (750 hours per month for eligible new accounts).

---

### 4. Security Groups (Virtual Firewalls)

A **Security Group** acts as a stateful virtual firewall that controls incoming (inbound) and outgoing (outbound) network traffic at the **EC2 Instance Level** (network interface level).

#### 🛡️ Core Security Group Principles:
* **Deny-All by Default:** All inbound traffic is blocked by default until explicit permission rules are added.
* **Stateful Filtering:** If an inbound request is permitted through a security group rule, the response traffic is automatically allowed back out, regardless of outbound rules (and vice-versa).
* **Rule Definitions:** Rules specify the Protocol (TCP, UDP, ICMP), Port Range (e.g., `22`, `80`, `443`), and Source/Destination (CIDR IP ranges like `0.0.0.0/0` or other Security Group IDs).

##### Enterprise Security Group Configuration Example:

```text
INBOUND RULES:
├── Port 22   (SSH)   ──► Source: 203.0.113.5/32 (Restricted to Developer Office IP)
├── Port 80   (HTTP)  ──► Source: 0.0.0.0/0      (Public Web Access)
└── Port 443  (HTTPS) ──► Source: 0.0.0.0/0      (Public Secure Web Access)

OUTBOUND RULES:
└── All Traffic        ──► Destination: 0.0.0.0/0 (Allows instance to reach internet)
```

5. Key Pairs & Secure SSH ConnectionAmazon EC2 uses public-key cryptography to protect instance login credentials. AWS generates and stores the Public Key on the EC2 instance, while you download and store the Private Key (a `.pem `file) on your local machine.

⚠️ Security Rule: You cannot retrieve the private key again after creation. If you lose your `.pem` file, you lose administrative access to the instance.

💻 Walkthrough: Launching & Connecting to an EC2 Instance
Step 1: Launching via AWS Management Console
1. Navigate to the EC2 Dashboard and click Launch Instance.
2. Name: Assign a tag (e.g., `production-web-server`).
3. Application and OS Image (AMI): Select Amazon Linux 2023 AMI or Ubuntu 22.04 LTS.
4. Instance Type: Select `t2.micro` (or `t3.micro` based on region eligibility).
5. Key Pair: Select an existing key pair or click Create new key pair (select RSA, `.pem` format, and save `my-ec2-key.pem` locally).
6. Network Settings:
     * Check Allow SSH traffic from $\rightarrow$ Set to My IP (for security).
     * Check Allow HTTP traffic from the internet (0.0.0.0/0).
7. Click Launch Instance.

Step 2: Connecting to a Linux Instance via Terminal (SSH)
1. Open your local terminal shell and navigate to the folder containing your downloaded .pem file:
```Bash
cd ~/Downloads
```

2. Restrict Permissions (Crucial Step): SSH will reject key connections if your private key file is readable by other users on your OS:
```Bash
# Set read-only permissions for the file owner
chmod 400 my-ec2-key.pem
```
3. Establish the SSH Connection:
```Bash
# Syntax: ssh -i <path-to-key.pem> <default-user>@<ec2-public-ip-or-dns>

# For Amazon Linux 2023:
ssh -i my-ec2-key.pem ec2-user@54.210.45.12

# For Ubuntu:
ssh -i my-ec2-key.pem ubuntu@54.210.45.12
```

---
## 🚢 Module: Amazon ECS – Container Basics & Managed Orchestration

As cloud applications scale, manually launching individual EC2 instances to host Docker containers becomes inefficient. **Amazon Elastic Container Service (Amazon ECS)** is AWS’s fully managed container orchestration service that allows you to run, scale, and secure containerized applications across a distributed cluster of servers.

---

### 1. Docker & Container Fundamentals

#### 📦 What is a Container?
A **container** is a lightweight, standalone, executable package that includes everything needed to run an application: code, runtime, system tools, system libraries, and settings. 

* **Process-Level Isolation:** Unlike Virtual Machines (VMs), which virtualize physical hardware and require an entire guest operating system, containers share the host operating system kernel.
* **Consistency:** Eliminates the "it works on my machine" problem by ensuring software executes identically across development, staging, and production environments.

---

### 2. What is Amazon ECS?

**Amazon Elastic Container Service (Amazon ECS)** is a high-performance, highly scalable container management service provided by AWS. It allows you to run containerized applications on AWS without needing to install, operate, or scale your own container orchestration software (like manual Docker Swarm or Kubernetes clusters).

ECS integrates natively with core AWS infrastructure services, including:
* **Elastic Load Balancing (ELB):** Automatically routes incoming web traffic across running containers.
* **AWS IAM:** Provides fine-grained IAM roles at the individual container level.
* **Amazon CloudWatch:** Tracks container health, CPU/memory metrics, and streams log files.
* **Amazon ECR (Elastic Container Registry):** Serves as a secure private Docker image repository.

---

### 3. Amazon ECS vs. Amazon EC2: Key Differences

Understanding the operational boundary between EC2 and ECS is critical for designing cloud-native architectures:

#### 🖥️ Amazon EC2 (Virtual Machines)
* Provides raw Virtual Machines (IaaS) where you manage the OS, security patches, system libraries, and runtime installations.
* You are responsible for scheduling, starting, and monitoring individual container processes manually on each machine.

#### 🚢 Amazon ECS (Container Orchestration)
* Operates *above* raw instances to coordinate where and how containers execute across a cluster.
* Automatically handles task scheduling, container placement, auto-scaling, load balancer target group registrations, and self-healing when containers fail.

#### 💡 ECS Compute Launch Types: EC2 vs. AWS Fargate

When using ECS, you choose how the underlying infrastructure is managed:

```text
               ┌─────────────────────────────────────────────────┐
               │         AMAZON ECS ORCHESTRATION LAYER          │
               └────────────────────────┬────────────────────────┘
                                        │
             ┌──────────────────────────┴──────────────────────────┐
             ▼                                                     ▼
┌─────────────────────────┐                               ┌─────────────────────────┐
│   1. EC2 Launch Type    │                               │  2. Fargate Launch Type │
│  (Managed Server Pool)  │                               │       (Serverless)      │
├─────────────────────────┤                               ├─────────────────────────┤
│ • You provision & manage│                               │ • Zero servers to manage│
│   EC2 instances.        │                               │ • Pay only for vCPU &   │
│ • Full OS control.      │                               │   RAM per task.         │
│ • Cost-effective for    │                               │ • Fully automated       │
│   steady workloads.     │                               │   capacity scaling.     │
└─────────────────────────┘                               └─────────────────────────┘
```

4. Core Structural Concepts in Amazon ECS
Amazon ECS uses a specific operational hierarchy to manage applications:

1. Task Definition (The Blueprint): A JSON file that describes one or more containers (up to 10) that form your application. It specifies parameters like Docker images, memory/CPU allocation, port mappings, environment variables, and log configurations.

2. Task (The Running Instance): The actual runtime instantiation of a Task Definition within a cluster.

3. Service (The Maintainer): Ensures that the specified number of tasks are constantly running and healthy across the cluster. If a task crashes, the ECS Service automatically replaces it. It also registers running tasks with a load balancer.

4. Cluster (The Logical Grouping): A logical grouping of tasks or services running either on EC2 instances or on AWS Fargate.

💻 Walkthrough: Creating & Managing Containers in Amazon ECS
Follow this production workflow to build, register, and launch a containerized microservice on Amazon ECS:

Step 1: Push Image to Amazon ECR (Elastic Container Registry)
```Bash
# 1. Authenticate your local Docker CLI against your AWS ECR Registry
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 123456789012.dkr.ecr.us-east-1.amazonaws.com

# 2. Build and tag your local application image
docker build -t my-web-app:v1.0 .
docker tag my-web-app:v1.0 [123456789012.dkr.ecr.us-east-1.amazonaws.com/my-web-app:v1.0](https://123456789012.dkr.ecr.us-east-1.amazonaws.com/my-web-app:v1.0)

# 3. Push the image to Amazon ECR
docker push [123456789012.dkr.ecr.us-east-1.amazonaws.com/my-web-app:v1.0](https://123456789012.dkr.ecr.us-east-1.amazonaws.com/my-web-app:v1.0)
```
Step 2: Define an ECS Task Definition (task-definition.json)
File: `task-definition.json`
```JSON
{
  "family": "web-app-task",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "256",
  "memory": "512",
  "executionRoleArn": "arn:aws:iam::123456789012:role/ecsTaskExecutionRole",
  "containerDefinitions": [
    {
      "name": "web-container",
      "image": "[123456789012.dkr.ecr.us-east-1.amazonaws.com/my-web-app:v1.0](https://123456789012.dkr.ecr.us-east-1.amazonaws.com/my-web-app:v1.0)",
      "portMappings": [
        {
          "containerPort": 80,
          "hostPort": 80,
          "protocol": "tcp"
        }
      ],
      "essential": true
    }
  ]
}
```
Step 3: Register Task and Launch Service via AWS CLI
```Bash
# 1. Register the JSON task definition with ECS
aws ecs register-task-definition --cli-input-json file://task-definition.json

# 2. Create an ECS Cluster
aws ecs create-cluster --cluster-name production-cluster

# 3. Launch an auto-healing ECS Service (Runs 2 replicas across Fargate)
aws ecs create-service \
  --cluster production-cluster \
  --service-name web-app-service \
  --task-definition web-app-task \
  --desired-count 2 \
  --launch-type FARGATE \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-abc12345],securityGroups=[sg-xyz67890],assignPublicIp=ENABLED}"
```

---

## 🪣 Module: Amazon S3 – Buckets, Objects & Storage Classes

**Amazon Simple Storage Service (Amazon S3)** is an object storage service offering industry-leading scalability, data availability, security, and performance. Unlike traditional file systems that store data in a hierarchical tree, S3 stores flat objects in flat logical containers called **Buckets**.

---

### 1. Core Concepts: Buckets and Objects

#### 🪣 1. Buckets
* A **Bucket** is a top-level container for storing data in Amazon S3.
* **Global Unique Namespace:** Bucket names must be **globally unique** across all AWS accounts worldwide (e.g., `my-company-prod-logs-2026`).
* **Region Specificity:** Although bucket names are global, every bucket resides physically in a specific AWS Region selected during creation.

#### 📄 2. Objects
* An **Object** is a file along with any metadata that describes that file.
* **Composition:** An object consists of the **Data Payload** (the actual file content), a **Key** (the unique string identifier/path name within the bucket), and **Metadata** (key-value pairs describing the object, such as content type, creation date, or security tags).
* **Storage Limit:** Individual objects can range in size from $0$ bytes up to a maximum of **5 Terabytes (5 TB)**.

---

### 2. S3 Bucket Security & Permissions (Public vs. Private)

By default, all newly created Amazon S3 buckets and their contents are **strictly private**. Access must be explicitly granted.

#### 🛡️ Core Security Controls:

* **Block Public Access Settings:** A account-wide and bucket-level security guardrail that prevents accidental public exposure of data. It overrides all bucket policies and Object ACLs.
* **Bucket Policies:** JSON-based access control policies attached directly to a bucket to grant or deny granular permissions across the entire bucket or specific object paths.
* **IAM Policies:** User-based permission scripts assigned to AWS users or roles controlling what S3 actions they are permitted to execute.
* **Access Control Lists (ACLs):** Legacy, object-level permission mechanisms (now disabled by default in modern enterprise setups in favor of Bucket Policies).

##### Enterprise Example: Public Read-Only Bucket Policy
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::my-public-static-website-bucket/*"
    }
  ]
}
```

3. Amazon S3 Storage Classes
Amazon S3 offers a range of storage classes designed for different data access patterns, retention requirements, and cost constraints.

🗄️ Storage Classes Overview:
1. S3 Standard:
 * High durability ($99.999999999\%$), high availability, and low-latency performance.
 * Best for frequently accessed data, cloud applications, dynamic websites, and content distribution.
2. S3 Intelligent-Tiering:
   * Automatically optimizes storage costs by moving objects between access tiers based on changing access patterns without operational overhead or retrieval fees.
   * Best for data with unknown, changing, or unpredictable access patterns.
3. S3 Standard-Infrequent Access (S3 Standard-IA):
    * Designed for data that is accessed less frequently but requires rapid millisecond access when needed. Lower storage cost than Standard, but charges a small retrieval fee per GB.
    * Best for long-term storage, backups, and disaster recovery files.
4. S3 Glacier Flexible Retrieval (Glacier):
  * Low-cost archival storage for data retained for years. Retrieval times range from minutes to several hours depending on the request priority (Expedited, Standard, or Bulk).
  * Best for historical archives, compliance backups, and regulatory records.
5. S3 Glacier Deep Archive:
  * The lowest-cost storage class in AWS. Retrieval times typically range within 12 to 48 hours.
  * Best for long-term digital preservation and archival data accessed once or twice a year.

4. Lifecycle Policies and Object Versioning
🔄 Object Versioning
* What it is: Keeps multiple variants of an object in the same bucket.
* Protection: Protects data against accidental deletion or overwrite operations. When you "delete" an object in a version-enabled bucket, S3 inserts a Delete Marker instead of permanently erasing the file.
* Un-deleting Files: You can restore previous versions at any time by removing the Delete Marker or requesting a specific Version ID.

⚙️ Lifecycle Policies
* What it is: A set of automated rules configured on a bucket to manage objects throughout their lifecycle.
* Automated Actions:
  * Transition Actions:Move objects to cost-effective storage classes as they age (e.g., transition objects from S3 Standard to S3 Standard-IA after $30$ days, and then to S3 Glacier after $90$ days).
  * Expiration Actions: Permanently delete non-current object versions or obsolete files after a specified number of days (e.g., purge temp log files after $365$ days).

💻 Walkthrough: Managing S3 Resources via AWS CLI1. 
1. Creating a Unique Bucket
```Bash
# Create an S3 Bucket in a specific region
aws s3api create-bucket \
    --bucket my-enterprise-app-storage-2026 \
    --region us-east-1
```
2. Enabling Versioning
```Bash
# Enable object versioning on the newly created bucket
aws s3api put-bucket-versioning \
    --bucket my-enterprise-app-storage-2026 \
    --versioning-configuration Status=Enabled
```
3. Uploading and Downloading Objects
```Bash
# Upload a local file to the bucket with explicit encryption
aws s3 cp application-logs.txt s3://my-enterprise-app-storage-2026/logs/application-logs.txt

# Download the file back from S3 to your local host
aws s3 cp s3://my-enterprise-app-storage-2026/logs/application-logs.txt ./downloaded-logs.txt
```
4. Applying a Lifecycle Policy (lifecycle.json)
File: `lifecycle.json`
```JSON
{
  "Rules": [
    {
      "ID": "MoveToIAAndArchive",
      "Status": "Enabled",
      "Filter": { "Prefix": "logs/" },
      "Transitions": [
        {
          "Days": 30,
          "StorageClass": "STANDARD_IA"
        },
        {
          "Days": 90,
          "StorageClass": "GLACIER"
        }
      ],
      "Expiration": {
        "Days": 365
      }
    }
  ]
}
```
```Bash
# Apply the lifecycle JSON script to the bucket
aws s3api put-bucket-lifecycle-configuration \
    --bucket my-enterprise-app-storage-2026 \
    --lifecycle-configuration file://lifecycle.json
```

---

## 💾 Module: Amazon EBS – Block Storage for EC2

**Amazon Elastic Block Store (EBS)** provides persistent block-level storage volumes for use with Amazon EC2 instances. Think of an EBS volume as a virtual hard drive attached to your server.

---

### 1. Key Concepts

* **Persistent Storage:** Data stored on an EBS volume persists independently of the life of the EC2 instance (it remains intact even if the instance is stopped or restarted).
* **Availability Zone Bound:** An EBS volume is created within a specific Availability Zone (AZ) and can only be attached to an EC2 instance in that same AZ.

---

### 2. Common EBS Volume Types

* **`gp3` (General Purpose SSD - Latest Generation):** The default cost-effective choice. Provides a baseline of 3,000 IOPS and 125 MB/s throughput regardless of size. Best for web applications, dev/test environments, and boot volumes.
* **`gp2` (General Purpose SSD - Legacy):** Older generation where IOPS scale proportionally with volume size (3 IOPS per GB).
* **`io1` / `io2` (Provisioned IOPS SSD):** Designed for high-performance, I/O-intensive database workloads (e.g., MongoDB, Oracle, PostgreSQL) requiring guaranteed performance.

---

### 3. EBS Snapshots & Backups

* **EBS Snapshots:** Point-in-time, incremental backups of your EBS volumes saved automatically to Amazon S3.
* **Incremental Backup:** Only the blocks on the volume that have changed since the last snapshot are saved, reducing storage costs.
* **Restoration:** Snapshots can be used to instantly create new EBS volumes or migrate volumes to different Availability Zones.

---

### 🛠️ Common AWS CLI Operations for EBS

```bash
# Create a 20 GB gp3 volume in us-east-1a
aws ec2 create-volume --volume-type gp3 --size 20 --availability-zone us-east-1a

# Attach an EBS volume to an EC2 instance
aws ec2 attach-volume --volume-id vol-0123456789abcdef0 --instance-id i-0123456789abcdef0 --device /dev/sdf

# Create a point-in-time snapshot of a volume
aws ec2 create-snapshot --volume-id vol-0123456789abcdef0 --description "Daily Database Backup"
```
🌐 Module: Amazon VPC – Networking Architecture
Amazon Virtual Private Cloud (VPC) allows you to provision a logically isolated section of the AWS Cloud where you can launch resources in a virtual network that you define.

1. Subnets & Route Tables
VPC: Your private, isolated virtual data center in the cloud defined by a CIDR IP block (e.g., 10.0.0.0/16).

Public Subnet: A network segment whose route table points traffic to an Internet Gateway (IGW). Resources here (e.g., web servers) can have public IP addresses and accept traffic directly from the internet.

Private Subnet: A network segment with no direct route to the internet. Resources here (e.g., backend databases) remain completely hidden from public access.

Route Tables: A set of network rules that control where network traffic leaving a subnet is directed.

2. Internet Gateway (IGW) vs. NAT Gateway
Internet Gateway (IGW): A horizontally scaled, highly available VPC component that enables two-way communication between resources in public subnets and the internet.

NAT Gateway (Network Address Translation): Placed in a public subnet to allow resources in private subnets to reach the internet (e.g., for software updates) while blocking incoming connections initiated from the internet.

3. Security Groups & VPC Peering
Security Groups: Stateful, instance-level virtual firewalls that control inbound and outbound traffic for EC2 instances.

VPC Peering: A private network connection between two VPCs that allows instances in either VPC to communicate with each other using private IP addresses as if they were on the same network.

📈 Traffic Flow Summary
```Plaintext
[ Internet ]
     │
     ▼
[ Internet Gateway (IGW) ]
     │
     ├────────► [ Public Subnet ] ──► (Web Server with Security Group)
     │                                     │
     ▼                                     ▼
[ NAT Gateway ] ───────────────► [ Private Subnet ] ──► (Database Server)
```
---
## ⚖️ Module: Elastic Load Balancing (ALB & NLB)

**Elastic Load Balancing (ELB)** automatically distributes incoming application traffic across multiple targets—such as EC2 instances, containers, and IP addresses—in one or more Availability Zones to ensure high availability and fault tolerance.

---

### 1. Application Load Balancer (ALB) vs. Network Load Balancer (NLB)

#### 🌐 Application Load Balancer (ALB)
* **OSI Layer:** Operates at **Layer 7** (Application Layer).
* **Protocols:** HTTP, HTTPS, HTTP/2, gRPC.
* **Key Features:** Advanced request routing based on URL path (e.g., `/api` vs `/static`), hostnames, or HTTP headers. Includes SSL/TLS termination.
* **Best Used For:** Web applications, microservices, and REST APIs.

#### ⚡ Network Load Balancer (NLB)
* **OSI Layer:** Operates at **Layer 4** (Transport Layer).
* **Protocols:** TCP, UDP, TLS.
* **Key Features:** Ultra-low latency, capable of handling millions of requests per second, supports static or Elastic IP addresses.
* **Best Used For:** Extreme high-performance workloads, gaming servers, and non-HTTP protocols.

---

### 2. Target Groups & Health Checks

* **Target Groups:** Logical groupings of resources (EC2 instances, ECS tasks, IP addresses) that receive routed traffic from the load balancer.
* **Health Checks:** Periodic ping requests (e.g., `GET /health`) sent to target instances. If an instance fails a health check, ELB automatically stops sending traffic to it until it recovers.

---

### 📊 Quick Comparison: ALB vs. NLB

| Feature | Application Load Balancer (ALB) | Network Load Balancer (NLB) |
| :--- | :--- | :--- |
| **OSI Layer** | Layer 7 (Application) | Layer 4 (Transport) |
| **Primary Protocols** | HTTP / HTTPS / gRPC | TCP / UDP / TLS |
| **Routing Capability** | Path, Host, & Header-based | Port & IP-based |
| **Performance Focus** | Complex application routing | Ultra-low latency & ultra-high throughput |

---

## 🗄️ Module: Amazon RDS – Managed Relational Databases

**Amazon Relational Database Service (Amazon RDS)** is a managed service that makes it easy to set up, operate, and scale relational databases in the cloud.

---

### 1. Managed vs. Self-Managed Databases

* **Self-Managed (EC2/On-Prem):** You must manually handle OS patching, database installation, software updates, backups, replication setups, and hardware failures.
* **Amazon RDS (Managed):** AWS handles time-consuming administrative tasks—including OS/DB patching, automated backups, hardware provisioning, and failover—allowing engineers to focus on data design and query performance.

---

### 2. Supported DB Engines

Amazon RDS supports six popular relational database engines:

1. **Amazon Aurora** (AWS-optimized MySQL/PostgreSQL compatible)
2. **PostgreSQL**
3. **MySQL**
4. **MariaDB**
5. **Microsoft SQL Server**
6. **Oracle**

---

### 3. Multi-AZ Deployments (High Availability)

* **How it Works:** RDS synchronously replicates your primary database instance to a **Standby Replica** located in a separate Availability Zone (AZ).
* **Automatic Failover:** If the primary DB instance fails or experiences an outage, RDS automatically promotes the standby replica to become the new primary without requiring DNS changes or human intervention.

---

### 4. Backups and Snapshots

* **Automated Backups:** Point-in-time recovery (PITR) automatically backs up database transaction logs and storage daily, allowing restoration to any second within a retention window (up to 35 days).
* **Manual DB Snapshots:** User-initiated point-in-time backups that are retained indefinitely until explicitly deleted.

---

### 🛠️ Common AWS CLI Commands for RDS

```bash
# Create a managed PostgreSQL database instance
aws rds create-db-instance \
    --db-instance-identifier prod-postgres-db \
    --db-instance-class db.t3.micro \
    --engine postgres \
    --allocated-storage 20 \
    --master-username adminuser \
    --master-user-password SecurityPassword123! \
    --multi-az

# Take a manual database snapshot
aws rds create-db-snapshot \
    --db-instance-identifier prod-postgres-db \
    --db-snapshot-identifier manual-db-backup-01
```
---

## ⚡ Module: Amazon DynamoDB – NoSQL Database Architecture

**Amazon DynamoDB** is a fully managed, serverless, key-value and document NoSQL database designed to deliver single-digit millisecond performance at any scale.

---

### 1. Key Concepts: Relational (RDS) vs. NoSQL (DynamoDB)

* **Amazon RDS (Relational/SQL):** Enforces rigid schemas, relational tables with foreign keys, and complex SQL joins. Best for structured data with complex query needs (e.g., financial transactions, ERP systems).
* **Amazon DynamoDB (NoSQL):** Schema-less tables optimized for key-value or document storage. Scales horizontally with virtually unlimited throughput and storage. Best for unstructured data, high-scale web apps, and real-time workloads (e.g., user sessions, gaming leaderboards, IoT data).

---

### 2. Primary Keys Structure

DynamoDB uses primary keys to uniquely identify items and distribute data across physical storage nodes:

* **Partition Key (PK) / Hash Key:** A single attribute that DynamoDB feeds into an internal hash function to determine the physical partition where the item is stored.
* **Sort Key (SK) / Range Key:** An optional second attribute that sorts items sharing the same Partition Key. Together, a Partition Key and Sort Key form a **Composite Primary Key**.

---

### 3. Querying vs. Scanning

* **Query (Fast & Efficient):** Searches items using only the Partition Key (and optionally a Sort Key condition). Performs targeted lookups with ultra-low latency and minimal resource consumption.
* **Scan (Slow & Expensive):** Reads every single item in the entire table and then filters out results. Scans consume high read capacity units (RCU) and should be avoided on large production tables.

---

### 🛠️ Common AWS CLI Commands for DynamoDB

```bash
# Create a DynamoDB table with a Composite Primary Key (UserID + OrderID)
aws dynamodb create-table \
    --table-name CustomerOrders \
    --attribute-definitions \
        AttributeName=UserID,AttributeType=S \
        AttributeName=OrderID,AttributeType=S \
    --key-schema \
        AttributeName=UserID,KeyType=HASH \
        AttributeName=OrderID,KeyType=RANGE \
    --billing-mode PAY_PER_REQUEST

# Put an item into the table
aws dynamodb put-item \
    --table-name CustomerOrders \
    --item '{"UserID": {"S": "usr_101"}, "OrderID": {"S": "ord_999"}, "Amount": {"N": "49.99"}}'

# Efficient Query lookup by Partition Key
aws dynamodb query \
    --table-name CustomerOrders \
    --key-condition-expression "UserID = :v1" \
    --expression-attribute-values '{":v1": {"S": "usr_101"}}'
```
🚀 Module: AWS Lambda – Serverless Computing & Event Triggers
AWS Lambda is a event-driven, serverless compute service that runs code automatically in response to events without requiring you to provision or manage servers.

1. What is Serverless Computing?
Serverless allows developers to build and run applications without worrying about server management, operating system updates, or capacity planning.

* Zero Infrastructure Overhead: AWS manages server provisioning, maintenance, OS patching, and scaling.

* Pay-Per-Execution Pricing: You are charged only for the compute time consumed (measured in milliseconds) and the number of requests—you pay nothing when your code is not running.

2. Event Sources & Triggers
Lambda functions execute automatically when triggered by changes or events in other AWS services:

* Amazon S3: Triggers code when a new file is uploaded (e.g., generating image thumbnails instantly).

* Amazon API Gateway: Triggers code when an HTTP REST API request is received.

* DynamoDB Streams: Triggers code when data inside a table is modified (e.g., sending a welcome email when a new user record is inserted).

3. Supported Runtimes
AWS Lambda natively supports multiple popular programming environments:
* Node.js
* Python
* Java
* C# (.NET)
* Go
* Ruby
* Custom runtimes via the Lambda Runtime API (Docker Containers).

💻 Walkthrough: Creating & Triggering a Lambda Function
1. Example Python Function (lambda_function.py)
```python
import json

def lambda_handler(event, context):
    # Extract data from the incoming event trigger
    print("Event Received:", json.dumps(event))
    
    return {
        'statusCode': 200,
        'body': json.dumps('Execution Successful from AWS Lambda!')
    }
```
2. Deploying Function via AWS CLI
```Bash
# Package the python script into a deployment zip
zip deployment.zip lambda_function.py

# Create the Lambda function
aws lambda create-function \
    --function-name ProcessOrderFunction \
    --runtime python3.11 \
    --role arn:aws:iam::123456789012:role/service-role/MyLambdaExecutionRole \
    --handler lambda_function.lambda_handler \
    --zip-file fileb://deployment.zip

# Manually invoke the Lambda function to test
aws lambda invoke \
    --function-name ProcessOrderFunction \
    response.json
```
---
## 🚪 Module: AWS API Gateway – Creating & Managing APIs

**Amazon API Gateway** is a fully managed service that makes it easy for developers to create, publish, maintain, monitor, and secure APIs at any scale. It acts as the "front door" for applications to access data, business logic, or functionality from backend services running on AWS Lambda, Amazon EC2, or public web endpoints.

---

### 1. Key Concepts & Architecture

* **REST APIs & HTTP APIs:** Flexible web APIs that allow developers to build stateless, request-response communication layers using standard HTTP protocols.
* **Serverless Front-End:** API Gateway handles the infrastructure for accepting and processing up to hundreds of thousands of concurrent API calls, including traffic management, authorization, CORS, and response caching.

---

### 2. Core Operational Components

* **Resources & Routes:** Logical paths that represent your endpoint URL structure (e.g., `/users`, `/orders/{id}`).
* **HTTP Methods:** Operations assigned to routes defining the HTTP verb: `GET`, `POST`, `PUT`, `DELETE`, or `PATCH`.
* **Integrations:** Connects an API route directly to a backend target—most commonly an **AWS Lambda function** (Serverless Backend) or an internal HTTP endpoint.

---

### 3. Deployment Stages

An API must be deployed to a **Stage** to be accessible to external clients over the web.

* **Environments:** Allows you to isolate different lifecycle environments such as `dev`, `staging`, and `prod`.
* **Stage Variables:** Key-value pairs used to pass environment-specific parameters (e.g., pointing `dev` stage calls to a development Lambda and `prod` stage calls to a production Lambda).

---

### 4. Traffic Control & API Security

* **Throttling & Rate Limits:** Protects backend services from traffic spikes and Denial of Service (DoS) attacks by setting **Rate Limits** (requests per second) and **Burst Limits**.
* **API Keys & Usage Plans:** Restricts API access to authorized users and tracks usage per client.
* **Authorization:** Integrates natively with **AWS IAM**, **Amazon Cognito User Pools**, or custom **Lambda Authorizers** to handle authentication and authorization before requests reach the backend.

---

### 💻 Walkthrough: Serverless Architecture Integration

```text
[ Client Request ] ──► [ API Gateway ] ──► [ AWS Lambda ] ──► [ DynamoDB ]
  (GET /users)         (Authentication     (Executes Code)    (Fetches Data)
                       & Throttling)
```
🛠️ Example: Deploying an API with AWS CLI

```bash
# 1. Create a REST API
aws apigateway create-rest-api --name 'UserServiceAPI' --region us-east-1

# 2. Deploy the API to a production stage
aws apigateway create-deployment \
    --rest-api-id abc123xyz \
    --stage-name prod \
    --stage-description 'Production Deployment'

# Access your live endpoint at:
# https://{rest-api-id}.execute-api.{region}[.amazonaws.com/prod/](https://.amazonaws.com/prod/)
```
