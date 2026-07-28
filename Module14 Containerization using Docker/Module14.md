### Containerization using Docker
## 🐳 Module: Essential Docker Commands & Container Operations

Containerization relies on manipulating lightweight execution instances via the Docker Daemon CLI. Mastering these basic Docker commands allows developers to fetch container templates, manage running processes, inspect memory filesystems, and interact dynamically with containerized applications.

---

### 1. Image Lifecycle Commands

Before a container can exist, Docker needs an **Image**—a read-only blueprint containing the application code, dependencies, libraries, and runtime instructions.

#### 📥 `docker pull`
Downloads an image blueprint from a remote container registry (such as Docker Hub) into your local machine's disk storage without running it immediately.

```bash
# Syntax: docker pull <repository-name>:<tag>
docker pull postgres:15-alpine
```

🖼️ `docker images`
Lists all container images currently stored on your local host system, including their repository names, tags, unique Image IDs, creation timestamps, and virtual sizes.

```Bash
docker images
```
Output Format Example:
Plaintext
| REPOSITORY  | TAG     |    IMAGE ID   |    CREATED     |   SIZE |
| :--- | :--- | :--- | :--- | :--- |
| postgres   |  15-alpine  | c70f6e5e8e11  | 2 weeks ago  |  379MB |
| nginx      |  latest     | a610669171f1  | 3 weeks ago  |  142MB |

🗑️ `docker rmi`
Removes one or more images from your local host machine storage to free up disk space.

Note: Docker will block you from deleting an image if an active or stopped container is still using it.

```Bash
# Syntax: docker rmi <image-id-or-name>
docker rmi postgres:15-alpine

# Force remove an image (if associated with stopped containers)
docker rmi -f c70f6e5e8e11
```
2. Container Execution & Process Management
Once an image exists locally, Docker spins up a Container—a runnable, isolated instance of that image.

🚀 `docker run`
Creates and starts a brand-new container instance from a target image. If the image does not exist locally, Docker will automatically execute a docker pull first before starting.

Crucial Flags for docker run:
* **`-d `(Detached mode):** Runs the container in the background, freeing up your terminal shell window.

* **`-p` (Port Mapping):** Binds a host machine port to a container internal port (-p <host_port>:<container_port>).

* **`--name:`** Assigns a custom human-readable name to the container instead of a random generated string.

* **`-e:`** Passes environment configuration variables inside the container space.

```Bash
# Spins up a detached Nginx container mapped to host port 8080
docker run -d -p 8080:80 --name my-web-server nginx:latest
```
📋 `docker ps`
Lists active containers running on your system.

```Bash
# Shows only currently active/running containers
docker ps

# Shows ALL containers (including stopped, exited, or failed instances)
docker ps -a
```
🛑 `docker stop`
Sends a graceful `SIGTERM` signal to a running container process, giving it a default 10-second timeout to complete active web requests, close database connections, and save states before shutting down.

```Bash
# Syntax: docker stop <container-id-or-name>
docker stop my-web-server
```
🧹 `docker rm`
Deletes a stopped container instance from memory and removes its writable container layer.

```Bash
# Removes a stopped container
docker rm my-web-server

# Force removes a container even if it is currently running (Sends SIGKILL)
docker rm -f my-web-server
```
3. Executing Commands Inside Running Containers
When diagnosing a container, you often need to attach terminal commands, inspect filesystems, or append parameters to running processes.

🐚 `docker exec`
Executes a new command inside a container that is already running. This is commonly used to open an interactive bash/sh terminal inside the container space.

Flags used with docker exec:
* **`-i` (Interactive):** Keeps STDIN open so you can type commands.

* **`-t` (TTY):** Allocates a pseudo-terminal shell interface.

```Bash
# Opens an interactive Bash terminal session inside a running container
docker exec -it my-web-server /bin/bash

# Executes a single quick command inside without opening a full interactive shell session
docker exec my-web-server cat /etc/nginx/nginx.conf
```
➕ Appending Commands During Container Startup (docker run `[IMAGE] [COMMAND]`)
When you issue a `docker run` statement, you can append a command at the end of the line. This overrides or appends to the default start command defined inside the container's original `Dockerfile.`

```Bash
# Starts a temporary Ubuntu container, overrides the default shell, and executes 'uname -a'
docker run --rm ubuntu:latest uname -a
```
(The --rm flag automatically cleans up and deletes the container instance immediately after execution ends).

🛠️ Real-World Command Walkthrough Cheat-Sheet
Here is a typical day-to-day lifecycle workflow executed in order:

```Bash
# Step 1: Pull an official Redis image from Docker Hub
docker pull redis:alpine

# Step 2: Verify the image downloaded to your local storage
docker images

# Step 3: Run Redis in detached background mode mapping port 6379
docker run -d -p 6379:6379 --name cache-db redis:alpine

# Step 4: Verify the container is running active
docker ps

# Step 5: Jump inside the running Redis container and execute Redis-CLI queries
docker exec -it cache-db redis-cli ping

# Step 6: Stop the Redis container gracefully
docker stop cache-db

# Step 7: View the stopped container status
docker ps -a

# Step 8: Remove the container instance and clean local image cache
docker rm cache-db
docker rmi redis:alpine
```
---

## 🚀 Module: Deep Dive into the `docker run` Command

The `docker run` command is the foundational operation in Docker container management. Under the hood, executing `docker run` triggers a multi-stage execution pipeline:
1. It checks if the specified image exists in the local daemon storage cache.
2. If absent, it automatically executes a `docker pull` to fetch the image from Docker Hub (or a configured private registry).
3. It creates a isolated, read-write container layer on top of the image structure.
4. It initializes network interfaces, assigns virtual IP parameters, maps host ports, and executes the default startup instruction.

---

### 1. Fundamental Command Anatomy

```bash
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```
When you issue `docker run hello-world`, Docker executes the container in the foreground, streams the output directly to your standard terminal output (`STDOUT`), and exits immediately once the execution process completes.
2. Core Execution Flags & Use Cases
A. Running a Container Under a Specific Name (`--name`)
By default, Docker assigns randomly generated names (e.g., `trusty_hopper`, `focused_tesla`) to new containers. In production systems, explicit naming is required so other scripts, orchestrators, and developers can identify and target the container deterministically.
```Bash
# Assigns the explicit identifier 'production-cache-db'
docker run --name production-cache-db redis:alpine
```
B. Running a Container in the Background / Detached Mode (`-d`)
Long-running background services (e.g., web servers, databases, messaging brokers) should not hold your terminal shell session hostage. The -d flag runs the container in Detached Mode, running the process silently in the background and returning the unique 64-character Container ID to your terminal shell.

```Bash
# Starts an Nginx web server as a background service process
docker run -d --name enterprise-web-node nginx:latest
```
To inspect output from a detached container later:
```Bash
docker logs enterprise-web-node
```

C. Running a Container Interactively (`-it`)
When you need to step into a container environment for debugging, shell operations, or manual configuration, combine the Interactive (`-i`) and Pseudo-TTY (`-t`) flags.
* **`-i` (Interactive):** Redirects your host shell terminal input (STDIN) directly into the container.
* **`-t` (TTY):** Allocates an interactive terminal interface inside the container.
```Bash
# Boots a fresh Ubuntu instance and attaches an interactive Bash shell session
docker run -it --name ubuntu-debug-node ubuntu:latest /bin/bash
```
(Type exit inside the container terminal to terminate the process and return to your host OS).

D. Publishing Container Network Ports (`-p`)
By default, containers operate inside an isolated virtual network bridge and are completely inaccessible to external traffic or host machine requests. The `-p` flag maps a Host Machine Port directly to an internal Container Port (`-p <host_port>:<container_port>`).
```Bash
# Maps host machine port 8080 to the container's internal web server port 80
docker run -d -p 8080:80 --name global-api-gateway nginx:latest
```

🌐 Traffic Execution Flow:
`External Web Client Request $\rightarrow$ Host Machine (Port 8080) $\rightarrow$ Docker Network Interface Bridge $\rightarrow$ Internal Container (Port 80)`
E. Auto-Removing Containers Post-Execution (`--rm`)
Running short-lived tasks (such as database migrations, compilation tasks, or one-off diagnostic scripts) leaves behind stopped, dead container files on disk. The `--rm ` flag tells Docker to automatically delete and purge the container instance and its file layer from disk memory the moment its process completes or exits.
```Bash
# Executes a one-off database check and instantly wipes out the container on completion
docker run --rm postgres:15-alpine pg_isready -h localhost
```

💻 Enterprise Command Comparison Blueprint
| Operational Goal| Command Execution Pattern | Primary Use Case | 
| :--- | :--- | :--- |
|Foreground Run | docker run alpine ping google.com | Testing short terminal script executions.|
| Named Service | docker run --name my-redis redis | Assigning predictable names for scripts.| 
| Detached Daemon | docker run -d -p 80:80 nginx | Hosting production web apps & microservices. |
|Interactive Terminal | docker run -it alpine /bin/sh | Manual inspection and runtime troubleshooting. |
| Ephemeral Tasks | docker run --rm node:18-alpine node -v | Running build tools or test suits without residue. |

🛠️ Integrated Master Execution Scenario
Here is an enterprise setup combining these flags into a single operational command:

```Bash
docker run -d \
  --name payment-gateway-service \
  -p 9090:8080 \
  -e SPRING_PROFILES_ACTIVE=production \
  --restart unless-stopped \
  my-company/payment-service:v2.1.0
```

🔍 Deconstructing the Flags:
* **`-d:`** Runs as a decoupled background service.

* **`--name payment-gateway-service:`** Grants a clean reference label for logging and operations.

* **`-p 9090:8080:`** Routes incoming host traffic on port 9090 into the Java container application listening on port 8080.

* **`-e SPRING_PROFILES_ACTIVE=production:`** Injects an environment configuration parameter into the container runtime space.

* **`--restart unless-stopped:`** Ensures the Docker daemon automatically revives the container if it crashes or if the host machine reboots.

* **`my-company/payment-service:v2.1.0:`** The specific version-tagged image blueprint executed by Docker.
---

## 📦 Module: Docker Images Architecture & Build Strategies

A **Docker Image** is an immutable, read-only template containing the application source code, runtime, system tools, libraries, and settings needed to execute a containerized application. Understanding image layers, base structures, manifests, and build contexts is essential for optimizing container performance and security.

---

### 1. Structural Architecture of Docker Images

An image is built as a stack of read-only layers, topped by a writable container layer at runtime.



#### 🥞 Image Layers (Copy-on-Write Strategy)
* Every instruction in a `Dockerfile` (e.g., `RUN`, `COPY`, `ADD`) creates an immutable, cached **Image Layer**.
* Docker uses a **Union File System (UnionFS)** to combine these separate layers into a single cohesive filesystem.
* **Copy-on-Write (CoW):** If two containers share the same base image, they share the exact same read-only layers in memory. If a container needs to modify a file from an underlying layer, it copies the file up into its own isolated writable layer before modifying it.

#### 📝 Container Layer
* When you start a container using `docker run`, Docker adds a thin, read-write **Container Layer** on top of the stacked read-only image layers.
* Any files created, modified, or deleted during runtime exist *only* inside this thin layer. When the container is deleted (`docker rm`), this writable layer is destroyed.

#### 🏗️ Base Image vs. Parent Image
* **Parent Image:** The underlying image that a custom `Dockerfile` references in its top `FROM` directive (e.g., `FROM openjdk:17-jdk-slim` or `FROM node:20-alpine`).
* **Base Image:** A foundational, top-level image layer built completely from scratch without a parent (e.g., `FROM scratch`). It typically contains minimal Linux utilities (like Alpine or Debian base files).

---

### 2. Registry, Repository, and Manifest Concepts

#### 📄 Docker Manifest
A **Manifest** is a JSON metadata document that describes a Docker image. It specifies layer digests, architecture tags (e.g., `amd64` vs `arm64`), CPU instructions, and operational configurations. Multi-architecture manifests allow a single image tag (like `nginx:latest`) to download the correct hardware binary automatically depending on whether it's pulled onto an Intel server or an Apple Silicon Mac.

#### 🏬 Container Registries vs. Repositories
* **Container Registry:** A centralized hosted service that stores and distributes container images (e.g., Docker Hub, Amazon ECR, GitHub Container Registry, Google Artifact Registry).
* **Container Repository:** A dedicated collection of related, version-tagged images within a registry (e.g., `docker.io/library/postgres`, where tags like `:14-alpine` and `:15-alpine` represent different versions inside the same repository).

---

### 3. Image Creation Methods

There are two primary ways to create custom Docker images:

#### Method A: The Interactive Method (`docker commit`)
*Used primarily for debugging, quick prototyping, or emergency troubleshooting.*

1. Spin up an interactive base container:
   ```bash
   docker run -it --name temp-editor ubuntu:latest /bin/bash
   ```

1) Manually install dependencies inside the running container session:

```Bash
apt-get update && apt-get install -y curl nodejs
exit
```

2) Commit the modified container layer into a brand-new persistent image:

```Bash
docker commit temp-editor my-custom-node-app:v1.0
```

⚠️ Enterprise Drawback: The interactive method produces untraceable, non-reproducible "black box" images that cannot be reliably audited in CI/CD pipelines.

Method B: The Dockerfile Method (Industry Standard)
Declarative, version-controlled, and fully automated build pipeline pattern.

A Dockerfile is a plain-text script containing the step-by-step instructions required to build an image deterministically.

## The Docker Build Context
When you run `docker build -t my-app:1.0 .`, the final argument (.) specifies the Build Context.

The Docker CLI recursively packs all files and directories inside that context path and transmits them up to the local Docker Daemon engine.

Best Practice: Always include a `.dockerignore` file in your root folder (similar to `.gitignore`) to exclude large binaries, `.git` histories, and `node_modules` from being sent to the daemon during the build process.

💻 Production Blueprint: Dockerfile Implementation
Here is an industry-standard, multi-stage Dockerfile for a Java application that optimizes layer caching and keeps the final image size small:

📄 File: `Dockerfile`
```Dockerfile
# ==========================================
# STAGE 1: Compilation Build Environment
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set internal working directory
WORKDIR /build

# Copy dependency configuration files first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy application source code into the build context
COPY src ./src

# Compile and package the production binary (.jar)
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Lightweight Runtime Environment
# ==========================================
FROM eclipse-temurin:17-jre-alpine AS runner

# Create a non-root system group and user for security compliance
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy ONLY the compiled .jar file from the builder stage layer
COPY --from=builder /build/target/*.jar app.jar

# Assign ownership to the non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root execution context
USER appuser

# Expose internal application port
EXPOSE 8080

# Configure healthcheck metric
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Define runtime execution entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
```

🔨 Building and Tagging the Image
Run the build command inside the directory containing the Dockerfile and .dockerignore:

```Bash
# Build command targeting current directory context (.)
docker build -t cognizant-registry.internal/apps/payment-service:v1.0.0 .
```
---

## 🐙 Module: Docker Compose & Multi-Container Orchestration

In enterprise microservices architectures, an application rarely runs as a single, isolated container. It typically requires web servers, application gateways, database engines, and caching layers to work together. Managing these manually using individual `docker run` commands becomes unmaintainable. **Docker Compose** simplifies this by allowing you to define and manage multi-container applications as a single declarative service stack.

---

### 1. What is Docker Compose?

**Docker Compose** is a tool for defining and running multi-container Docker applications. It uses a single **YAML configuration file** (`docker-compose.yml`) to configure your application's services, networks, and persistent storage volumes. 

With a single command, you can spin up, connect, and configure your entire application stack.

---

### 2. Primary Benefits of Docker Compose

* **Declarative Configuration:** Replaces long, error-prone terminal commands with a single version-controlled configuration script.
* **Isolated Environment Management:** Keeps your entire application stack logically grouped, establishing isolated virtual networks so containers can communicate cleanly using service names as hostnames.
* **Single-Command Control:** Boots, stops, rebuilds, and scales your entire architecture using simple unified operations.
* **Volume & State Persistence:** Automatically preserves database state and mapped directories across container restarts without manual re-binding.

---

### 3. Installation & Verification

Docker Compose is included natively with **Docker Desktop** on Windows and macOS. On Linux host systems, it is installed as a standalone CLI plugin component.

Verify your Docker Compose engine version via terminal:

```bash
docker compose version
```
Output Verification Example:
```Plaintext
Docker Compose version v2.24.5-desktop.1
```
4. Core Docker Compose CLI Commands
All docker compose operations must be executed from the directory containing your docker-compose.yml file.

🚀 `docker compose up`
Creates networks, volumes, pulls images, builds custom containers, and starts all defined services.

* **Use the -d flag to run all services in the background (Detached Mode).**

* **Use the --build flag to force Docker to recompile local images before starting.**

```Bash
docker compose up -d --build
```

🛑 `docker compose down`
Stops and removes all running service containers, networks, and internal interface bridges created by up.

Add `-v` to also purge named storage volumes (useful for completely resetting database states).

```Bash
docker compose down -v
```

📋 `docker compose ps`
Lists the execution status, mapped ports, and health indicators of all containers managed by the current Compose file.

```Bash
docker compose ps
```

📜 `docker compose logs`
Streams real-time terminal output logs from all running containers in the stack.

```Bash
# View aggregated logs for all services
docker compose logs -f

# View logs for a specific service (e.g., database)
docker compose logs -f postgres-db
```

🔄 `docker compose restart`
Restarts all container processes inside the stack without tearing down network mappings or volumes.

```Bash
docker compose restart
```

5. Deconstructing the YAML Configuration File
A docker-compose.yml file uses standard YAML (YAML Ain't Markup Language) syntax, which relies strictly on consistent indentation (spaces, not tabs).

Key Structural Elements:
services: Defines the individual container configurations (e.g., web, backend, database).

* **build:** Specifies a local build path containing a Dockerfile.

* **image:** Points to an existing image from a registry (e.g., postgres:15-alpine).

* **ports:** Defines external-to-internal port mappings (<host_port>:<container_port>).

* **environment:** Passes runtime environment variables into the container scope.

* **volumes:** Configures persistent host directory mounts or named Docker storage volumes.

* **depends_on:** Specifies startup dependency order (e.g., wait for the database service to start before launching the application API).

💻 Enterprise Blueprint: Complete Multi-Tier Application Stack
Here is an industry-standard docker-compose.yml file orchestrating a Java Spring Boot API Gateway, a React Frontend, a PostgreSQL Database, and a Redis Cache Layer:

File: `docker-compose.yml`
```YAML
version: '3.8'

services:
  # -------------------------------------------------------------
  # SERVICE 1: PostgreSQL Database Instance
  # -------------------------------------------------------------
  postgres-db:
    image: postgres:15-alpine
    container_name: enterprise-postgres
    restart: always
    environment:
      POSTGRES_DB: bank_production
      POSTGRES_USER: db_admin
      POSTGRES_PASSWORD: SecurePassWord123!
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U db_admin -d bank_production"]
      interval: 10s
      timeout: 5s
      retries: 5

  # -------------------------------------------------------------
  # SERVICE 2: Redis In-Memory Cache Layer
  # -------------------------------------------------------------
  redis-cache:
    image: redis:7-alpine
    container_name: enterprise-redis
    restart: always
    ports:
      - "6379:6379"
    networks:
      - app-network

  # -------------------------------------------------------------
  # SERVICE 3: Backend Java Spring Boot API (Local Build Context)
  # -------------------------------------------------------------
  backend-api:
    build:
      context: ./backend-service
      dockerfile: Dockerfile
    container_name: enterprise-backend
    restart: on-failure
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-db:5432/bank_production
      SPRING_DATASOURCE_USERNAME: db_admin
      SPRING_DATASOURCE_PASSWORD: SecurePassWord123!
      SPRING_REDIS_HOST: redis-cache
      SPRING_REDIS_PORT: 6379
    depends_on:
      postgres-db:
        condition: service_healthy
      redis-cache:
        condition: service_started
    networks:
      - app-network

  # -------------------------------------------------------------
  # SERVICE 4: React Web Frontend Interface
  # -------------------------------------------------------------
  frontend-ui:
    build:
      context: ./frontend-ui
      dockerfile: Dockerfile
    container_name: enterprise-frontend
    ports:
      - "80:80"
    depends_on:
      - backend-api
    networks:
      - app-network

# ---------------------------------------------------------------
# SHARED RESOURCES DECLARATION
# ---------------------------------------------------------------
networks:
  app-network:
    driver: bridge

volumes:
  postgres_data:
    driver: local
```
🛠️ Operational Command Sequence
Navigate to the directory containing the file above and launch the entire multi-tier cluster in detached mode:

```Bash
docker compose up -d
```

Verify that all 4 containers are running and healthy:

```Bash
docker compose ps
```
Tear down the stack and clean up resources when done:

```Bash
docker compose down
```
---

## ⚙️ Module: Deep Dive into Docker Engine Architecture

**Docker Engine** is the core client-server technology that builds, ships, and runs containerized applications. It acts as the underlying execution platform that abstracts host operating system kernel resources to isolate microservices.


### 1. The Client-Server Architectural Model

Docker Engine does not run as a single monolithic process. Instead, it relies on a decoupled, three-tier architecture:

[ Docker CLI (Client) ] ──(HTTP / REST API)──> [ Docker Daemon (dockerd) ] ──> [ Containers & Images ]

### 2. Core Components Breakdown

#### 💻 1. The Docker CLI (Client)
* **What it is:** The command-line interface (`docker`) used by developers to interact with Docker.
* **How it works:** The CLI does **not** create containers or build images directly. When you execute a command (like `docker run` or `docker build`), the CLI translates your terminal input into an HTTP REST API request and sends it to the background daemon engine for execution.
* **Flexibility:** Because the CLI communicates over HTTP/sockets, a local CLI on your laptop can manage a remote Docker Daemon running on an AWS cloud server seamlessly.

#### 🔌 2. The Docker Engine REST API
* **What it is:** A standardized RESTful HTTP API endpoint exposed by the Docker Daemon server.
* **How it works:** It acts as the bridge interface between the client and the server daemon. It accepts payload requests from the Docker CLI, custom SDKs (like Java or Python Docker Clients), or CI/CD tools (like Jenkins) to trigger operations programmatically.

#### ⚙️ 3. The Docker Daemon (`dockerd`)
* **What it is:** The persistent, non-graphical background process that runs on the host operating system.
* **How it works:** It is the actual workhorse of the Docker platform. The daemon listens continuously for incoming REST API requests and handles heavy system operations:
  * Managing host container execution lifecycles.
  * Building and caching read-only Image layers.
  * Allocating virtual Linux network bridges and interfaces.
  * Managing persistent storage data volumes.
  * Communicating directly with underlying OS kernel isolation primitives (`cgroups` and `namespaces`).

---

### 3. Execution Sequence: What Happens During `docker run`

To see how these three architectural layers interact in real time, trace what happens under the hood when you run a container:

```bash
docker run -d -p 80:80 nginx
```
1. Client Trigger (Docker CLI): The user types the command into their terminal shell. The CLI parses the arguments and formats an HTTP POST request payload targeting /containers/create.

2. API Handshake (REST API): The Docker CLI transmits the request over a Unix Domain Socket (/var/run/docker.sock) to the REST API layer.

3. Daemon Processing (dockerd):

* **The daemon intercepts the HTTP API payload.**

* **It checks its local cache layer for the nginx image. If missing, it communicates with Docker Hub to pull down the missing image layers.**

* **It requests containerd (the low-level container runtime) to spin up an isolated execution container using host OS namespaces (for process isolation) and control groups (for RAM/CPU allocation limits).**

* **It configures host port mapping 80:80 on the network bridge.**

4. Client Response: The daemon sends a 201 Created HTTP response back through the API to the CLI, printing the unique 64-character Container ID to the terminal shell window.

💻 Interacting Directly with the Docker Engine REST API
Since the Docker CLI is just an API wrapper, you can bypass the CLI entirely and communicate directly with the Docker Daemon using standard HTTP commands like curl via the local socket:

Fetching Docker System Info via Socket API:
```Bash
# Querying the Docker Daemon REST API endpoint directly using cURL
sudo curl --unix-socket /var/run/docker.sock http://localhost/v1.43/info
```

Output Json Payload Sample:
```JSON
{
  "ID": "7J9X:D45L:3M2P:8Q9N",
  "Containers": 3,
  "ContainersRunning": 1,
  "ContainersPaused": 0,
  "ContainersStopped": 2,
  "Images": 12,
  "Driver": "overlay2",
  "OperatingSystem": "Ubuntu 22.04.3 LTS",
  "NCPU": 8,
  "MemTotal": 16777216000
}
```
---

## 💾 Module: Docker Storage Architecture & Volume Operations

By default, all files created inside a running container are stored on a temporary, writable container layer. This storage model presents two major architectural challenges: data created inside a container does not persist when the container is deleted, and writing to a container's layer requires a performance-heavy storage driver translation layer. To handle persistent data and achieve optimal disk I/O performance, Docker separates storage into **Storage Drivers** and **Data Volumes**.

---

### 1. Understanding Storage Drivers vs. Data Volumes

#### A. Storage Drivers (Ephemeral Layer Management)
Storage Drivers manage the read-write filesystem layers of containers using a **Union File System (UnionFS)** and a **Copy-on-Write (CoW)** strategy. 
* **How It Works:** When a container reads a file, the driver accesses it directly from the underlying read-only image layers. When a container *modifies* that file, the storage driver copies the file up into the container's writable layer before saving the changes.
* **Common Drivers:** `overlay2` (the default, highly optimized Linux driver), `btrfs`, `zfs`, and `vfs`.
* **Performance Impact:** Writing heavily to a container layer through a storage driver adds CPU and I/O latency. High-performance tasks (such as database writes) should never rely on the storage driver.

#### B. Data Volumes (Persistent Host Storage)
Data Volumes completely bypass the storage driver and Union File System. 
* **How It Works:** A Data Volume is a specially designated directory created on the host machine's filesystem (typically under `/var/lib/docker/volumes/`). 
* **Key Properties:** Volumes are managed directly by Docker, completely independent of the container lifecycle. Deleting a container *never* deletes its associated volume. Volumes offer native disk read/write performance equal to running directly on the host OS.

---

### 2. Changing the Storage Driver for Docker Engine

Storage drivers are configured at the Docker Engine daemon level, not on individual containers. To change the storage driver globally across your host:

1. Stop the active Docker Daemon service process.
2. Edit the primary daemon configuration file located at `/etc/docker/daemon.json`.
3. Update or insert the `storage-driver` key parameter:
   ```json
   {
     "storage-driver": "overlay2"
   }
   ```
Restart the Docker Daemon. (Note: Changing storage drivers hides existing containers and images created under the old driver, requiring you to re-fetch or rebuild them).

3. Managing Docker Volumes
Docker provides dedicated CLI commands to manage persistent storage resources across application lifecycles.

A. Creating a Named Volume
To provision an isolated, managed directory on the host storage filesystem:

```Bash
docker volume create postgres_prod_data
```
B. Inspecting Volume Metadata
To view detailed storage paths, driver allocations, labels, and mount points on the host system:

```Bash
docker volume inspect postgres_prod_data
```
C. Listing All Managed Volumes
To display all persistent storage volumes currently managed by the Docker host daemon:

```Bash
docker volume ls
```
D. Removing Unused Storage Volumes
To clean up dangling or unattached volumes that are no longer bound to any active or stopped containers:

```Bash
docker volume prune
```

## 🌐 Module: Docker Networking & Network Architecture

Docker networking allows containers to communicate with each other, with the host machine, and with external network endpoints. Docker uses an abstract networking model called the **Container Network Model (CNM)**, which isolates container interfaces into logical networks for security, routing, and dynamic service discovery.

---

### 1. Default Docker Networks

When you install Docker Engine, the daemon automatically creates three default network drivers on your host machine:

#### 1. Bridge Network (`bridge`)
* **What it is:** The default network driver for standalone containers. 
* **How it works:** Docker creates a virtual network interface bridge (typically named `docker0`) on the host. Containers connected to this bridge receive an internal private IP address (e.g., `172.17.0.x`).
* **Port Binding:** Containers on the default bridge can talk to each other via internal IP addresses, but external traffic can only reach them if ports are explicitly exposed using `-p` (e.g., `-p 8080:80`).

#### 2. Host Network (`host`)
* **What it is:** Removes network isolation between the container and the Docker host.
* **How it works:** The container shares the host's exact network namespace and IP address directly. The container does not receive its own isolated IP address.
* **Performance:** Provides maximum network throughput because there is no Network Address Translation (NAT) overhead. However, port conflicts can occur if multiple containers try to bind to the same host port.

#### 3. None Network (`none`)
* **What it is:** Completely disables container networking.
* **How it works:** The container is provisioned with a loopback interface only (`127.0.0.1`), with no access to external networks or other containers.
* **Best For:** High-security batch processing jobs, isolated data parsing, or security compliance testing where zero network exposure is required.

---

### 2. Managing Docker Networks via CLI

Docker provides dedicated commands to inspect, create, and manage virtual network drivers.

#### 📋 Listing All Docker Networks
Displays all active network bridges, host interfaces, custom networks, and driver types on the host system:

```bash
docker network ls
```
Output Verification Example:
Plaintext
| NETWORK ID  |   NAME   |   DRIVER  |  SCOPE |
| :--- | :--- | :--- | :--- |
| e3b9f4c8a1b2 |  bridge  |  bridge  |  local |
| 8f1a2b3c4d5e |  host    |  host    |  local |
| 0a9b8c7d6e5f |  none    |   null   |  local |

🔍 Inspecting a Docker Network
Provides a detailed JSON breakdown of a network's configuration, including gateway IPs, subnets, and every container currently attached to it:

```Bash
docker network inspect bridge
Output Json Payload Sample:
JSON
[
    {
        "Name": "bridge",
        "Id": "e3b9f4c8a1b2...",
        "Driver": "bridge",
        "IPAM": {
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Containers": {
            "a1b2c3d4e5f6...": {
                "Name": "enterprise-web",
                "IPv4Address": "172.17.0.2/16"
            }
        }
    }
]
```

🛠️ Creating Your Own Custom Network
While the default bridge network works out of the box, user-defined custom bridge networks are industry standard for production environments.

Why Use Custom Bridge Networks?
* **Automatic DNS Service Discovery:** Containers on a custom bridge network can ping and communicate with each other directly using their container names (e.g., `http://postgres-db:5432`), eliminating the need to hardcode volatile IP addresses.

* **Better Isolation:** Containers on distinct custom networks cannot communicate with each other, creating secure microservice boundaries.

```Bash
# Create a user-defined isolated bridge network
docker network create internal-app-net

# Run containers attached directly to the new custom network
docker run -d --name database-node --network internal-app-net postgres:15-alpine
docker run -d --name backend-api-node --network internal-app-net -p 8080:8080 my-api-image:v1

# Connect an already-running container to a network on the fly
docker network connect internal-app-net legacy-container
```
---

## ☸️ Module: Container Orchestration & Kubernetes Architecture

Running single containers using Docker CLI or Docker Compose works well for local development and small-scale applications. However, operating mission-critical enterprise systems across dozens of servers with thousands of container instances introduces complex operational challenges that manual runtime engines cannot handle.

---

### 1. What is Container Orchestration?

**Container Orchestration** is the automated management, scheduling, scaling, networking, and deployment of containerized applications across a clustered environment of physical or virtual machines. 

Instead of an engineer manually logging into individual servers to start containers, inspect health states, or re-route network traffic, an orchestration engine acts as a centralized brain. It continuously monitors the entire server cluster and automatically enforces your desired system state.

---

### 2. Why Do We Need Container Orchestration?

When scaling an application to production, several operational points of failure emerge:

* **Hardware Failures:** If a physical server hosting 20 container instances suddenly loses power or crashes, those containers instantly go offline.
* **Traffic Spikes:** When incoming web traffic surges, manual container scaling is too slow to prevent application crashes.
* **Deployment Downtime:** Updating an application without an orchestrator requires stopping old containers before starting new ones, causing immediate service interruptions for end-users.
* **Resource Waste:** Manually distributing containers across servers leads to uneven hardware utilization, leaving some servers overloaded while others sit idle.

Container orchestration solves these challenges by treating a pool of separate server machines as a single, unified computing platform.

---

### 3. Benefits of Container Orchestration

* **High Availability & Self-Healing:** If a container crashes or its host node dies, the orchestrator detects the failure immediately and spins up an identical replacement container on an active, healthy server.
* **Automated Scaling (Autoscaling):** Orchestrators track real-time CPU and memory usage, automatically launching new container instances during high demand and scaling them back down when traffic subsides.
* **Zero-Downtime Rolling Updates:** Updates are deployed incrementally across the cluster. The orchestrator routes traffic only to newly updated, healthy containers before taking old versions offline.
* **Dynamic Load Balancing:** Automatically distributes incoming network traffic evenly across all healthy container instances running across different nodes in the cluster.
* **Declarative Configuration Management:** Engineers write simple configuration files describing how the system *should* look (e.g., "Run 5 instances of the Payment Service"). The orchestrator handles the low-level logic to make it happen.

---

### 4. What is Kubernetes Container Orchestration?

**Kubernetes** (often abbreviated as **K8s**, where "8" replaces the eight letters between "K" and "s") is an open-source container orchestration platform originally designed by Google and maintained by the Cloud Native Computing Foundation (CNCF).

Kubernetes serves as the industry standard for enterprise container orchestration. It automates operational workflows by managing groups of containers into logical units called **Pods**, organizing infrastructure into **Worker Nodes**, and coordinating cluster management through a centralized **Control Plane**.

#### Core Responsibilities of Kubernetes:
* **Scheduling:** Deciding which physical or virtual server has enough memory and CPU to run a new container workload.
* **Service Discovery & DNS:** Assigning stable IP addresses and internal DNS hostnames to groups of containers.
* **Storage Orchestration:** Automatically mounting local disk storage, cloud storage volumes (like AWS EBS or Azure Disk), or network filesystems to container instances.
* **Secret & Configuration Management:** Storing passwords, API keys, and configuration profiles securely without baking them into container images.

---

### 5. Container Orchestration vs. Docker: Understanding the Difference

Comparing container orchestration to Docker is not a matter of choosing one *over* the other; rather, they operate at different layers of the modern technology stack.

#### Docker (The Container Runtime)
Docker is a **containerization technology** designed to package, build, and run individual containers on a single operating system instance. It focuses on the application runtime environment—creating container images, managing container layers, and isolating local processes. Docker excels at making applications portable across environments.

#### Container Orchestration / Kubernetes (The Cluster Engine)
Kubernetes is a **cluster management system** that operates *above* the container runtime level. Kubernetes does not build or package container images itself. Instead, it takes container images created by tools like Docker and manages how thousands of those containers run, scale, communicate, and recover across a distributed network of multiple servers.

#### Summary Relationship
* **Docker** creates and runs the individual containers.
* **Kubernetes** orchestrates, scales, and manages those Docker containers across a large server network.

---

### 💻 Enterprise Declarative Configuration Blueprint

To see how Kubernetes handles container orchestration declaratively, review this standard **Kubernetes Deployment Configuration Script**. It instructs the cluster to maintain 3 identical replicas of a containerized API service, run automated health checks, and perform zero-downtime rolling updates.

##### File: `deployment.yaml`

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: enterprise-payment-api
  labels:
    app: payment-service
spec:
  # ORCHESTRATION INSTRUCTION: Maintain exactly 3 active container instances across the cluster
  replicas: 3
  selector:
    matchLabels:
      app: payment-service
  
  # ROLLING UPDATE STRATEGY: Ensure zero-downtime during deployments
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1        # Allow 1 extra container during updates
      maxUnavailable: 0  # Ensure 0 downtime during updates

  template:
    metadata:
      labels:
        app: payment-service
    spec:
      containers:
      - name: payment-container
        # Image created by Docker and pulled from a container registry
        image: my-registry.internal/finance/payment-api:v2.1.0
        ports:
        - containerPort: 8080
        
        # RESOURCE ALLOCATION: Prevents any single container from overloading a host server
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"

        # SELF-HEALING HEALTHCHECK: K8s automatically restarts the container if this fails
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 15
          periodSeconds: 10
```

🛠️ Applying the Orchestration Rule
To hand this operational directive over to the Kubernetes cluster, run:

```Bash
kubectl apply -f deployment.yaml
```
