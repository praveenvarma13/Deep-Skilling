### Alige Methodology

## 🔄 Module: Agile Methodology & The Agile Manifesto

Modern software engineering requires rapid iteration, continuous customer feedback, and the flexibility to adapt to changing requirements. **Agile** is an iterative approach to software development and project management that helps teams deliver value to their customers faster and with fewer headaches. Instead of betting everything on a massive single launch, an Agile team delivers work in small, consumable increments.

---

### 1. What is Agile?

Historically, software development was treated like traditional construction: heavy upfront planning, rigid sequential execution, and late-stage delivery. **Agile** shifted this paradigm by acknowledging that initial project requirements are rarely static. Requirements evolve as market dynamics change and users interact with early software builds.

Agile focuses on breaking down large projects into manageable, time-boxed operational cycles called **iterations** or **sprints** (typically 1 to 4 weeks long). At the end of every sprint, the team ships a functional, tested software increment.

---

### 2. The Agile Manifesto: 4 Core Values

Formulated in 2001 by seventeen software developers, the **Agile Manifesto** outlines four foundational values that prioritize people, working software, and adaptability over rigid processes.

While there is value in the items on the right, Agile values the items on the **left** more:

1. **Individuals and Interactions** *over* Processes and Tools
   * *Meaning:* Brilliant tools and strict workflows cannot replace clear, open communication between cross-functional team members.
2. **Working Software** *over* Comprehensive Documentation
   * *Meaning:* A 200-page specification document yields no business value. Delivering functional, executable code that solves a user's problem takes precedence over extensive paper trails.
3. **Customer Collaboration** *over* Contract Negotiation
   * *Meaning:* Instead of locking customers into a rigid contract drawn up at day zero, teams work directly alongside stakeholders throughout development to refine product direction.
4. **Responding to Change** *over* Following a Plan
   * *Meaning:* Detailed long-term plans become obsolete quickly. Agile teams embrace changing requirements—even late in development—as a competitive advantage.

---

### 3. The 12 Principles of Agile

The four values are backed by **12 Guiding Principles** that inform day-to-day team execution:

1. **Customer Satisfaction:** Our highest priority is to satisfy the customer through early and continuous delivery of valuable software.
2. **Welcome Change:** Welcome changing requirements, even late in development. Agile processes harness change for the customer's competitive advantage.
3. **Frequent Delivery:** Deliver working software frequently, from a couple of weeks to a couple of months, with a preference for the shorter timescale.
4. **Collab Everyday:** Business people and developers must work together daily throughout the project.
5. **Motivated Individuals:** Build projects around motivated individuals. Give them the environment and support they need, and trust them to get the job done.
6. **Face-to-Face Conversation:** The most efficient and effective method of conveying information to and within a development team is face-to-face conversation.
7. **Working Software is Progress:** Working software is the primary measure of progress.
8. **Sustainable Pace:** Agile processes promote sustainable development. The sponsors, developers, and users should be able to maintain a constant pace indefinitely.
9. **Technical Excellence:** Continuous attention to technical excellence and good design enhances agility.
10. **Simplicity:** Simplicity—the art of maximizing the amount of work not done—is essential.
11. **Self-Organizing Teams:** The best architectures, requirements, and designs emerge from self-organizing teams.
12. **Reflect & Adjust:** At regular intervals, the team reflects on how to become more effective, then tunes and adjusts its behavior accordingly.

---

### 4. Agile vs. Waterfall Methodology

To understand why modern technology teams favor Agile, consider how it contrasts with the traditional **Waterfall** model:

#### Traditional Waterfall Model
* **Approach:** Linear, sequential design process (Requirements $\rightarrow$ Design $\rightarrow$ Implementation $\rightarrow$ Verification $\rightarrow$ Maintenance).
* **Flexibility:** Highly rigid. Changing scope late in the project requires expensive change requests.
* **Risk Profile:** High risk. Testing happens at the very end. If logical defects or architectural flaws are discovered during verification, fixing them requires massive refactoring.
* **Delivery:** Single, final release after months or years of development.

#### Agile Methodology
* **Approach:** Iterative, incremental cycle with continuous design, build, and test phases occurring simultaneously.
* **Flexibility:** Highly flexible. Backlogs are refined dynamically based on real-time feedback.
* **Risk Profile:** Low risk. Automated testing and continuous integration catch bugs within minutes or days of code generation.
* **Delivery:** Continuous, incremental releases providing usable software at the end of every sprint.

---

### 📈 Comparison Matrix: Agile vs. Waterfall

| Feature / Dimension | Waterfall Model | Agile Methodology |
| :--- | :--- | :--- |
| **Project Structure** | Sequential & Linear | Iterative & Incremental |
| **Requirement Changes** | Difficult and cost-prohibitive | Expected and welcomed |
| **Testing Lifecycle** | Conducted after full build phase | Integrated continuously into every sprint |
| **Stakeholder Feedback** | Ingested at start and final sign-off | Ingested continuously at sprint demos |
| **Team Structure** | Siloed (Devs, QA, Ops separated) | Cross-functional (Devs, QA, Product working together) |
| **Primary Metric of Success** | Adherence to initial plan & budget | Delivery of working, valuable software |

---

### 💻 Practical Workflow: Sprint Iteration Lifecycle

Here is how an Agile team translates these values into a standard 2-week execution loop:

```text
[ Product Backlog ] 
       │
       ▼ (Sprint Planning)
[ Sprint Backlog ] ──► [ 2-Week Sprint Execution ] ──► [ Daily Standups ]
                                │
                                ▼
                   [ Incrementally Shipped Software ]
                                │
                      ┌─────────┴─────────┐
                      ▼                   ▼
            [ Sprint Review / Demo ]  [ Sprint Retrospective ]
```

* **Sprint Planning:** The team pulls high-priority user stories from the Product Backlog into a focused Sprint Backlog.

* **Sprint Execution:** Developers write code and automated tests simultaneously, syncing daily via 15-minute Daily Standups to clear blockers.

* **Sprint Review:** The team demonstrates working software directly to stakeholders to capture feedback.

* **Sprint Retrospective:** The team reflects on internal team dynamics to optimize their processes for the upcoming sprint.

---

## 🏃 Module: Scrum Framework – Roles, Ceremonies & Artifacts

**Scrum** is a lightweight operational framework within the Agile methodology designed to help cross-functional teams deliver value through adaptive solutions to complex problems. Rather than attempting to manage every detail with detailed upfront plans, Scrum structures team execution using defined **Roles**, structured **Ceremonies** (Events), and clear **Artifacts**.

---

### 1. Scrum Roles: The Accountabilities

Scrum does not use traditional hierarchical project titles (like Project Manager or Tech Lead). Instead, a Scrum Team is small, cross-functional, and self-managing, consisting of three distinct accountabilities:

#### 🎯 Product Owner (PO)
* **Core Focus:** Maximizing the value of the product resulting from the work of the Scrum Team.
* **Responsibilities:**
  * Serves as the voice of the customer and business stakeholders.
  * Owns and manages the **Product Backlog** (creating, prioritizing, and clearly expressing user stories).
  * Decides *what* needs to be built and in what order to deliver maximum ROI.

#### 🛡️ Scrum Master (SM)
* **Core Focus:** Ensuring the team understands and adheres to Scrum theory, practices, and rules.
* **Responsibilities:**
  * Acts as a true servant leader, coaching the team on Agile principles.
  * Removes internal and external **blockers** (impediments) that slow down development progress.
  * Facilitates Scrum ceremonies and protects the team from external distractions or shifting priorities mid-sprint.

#### 💻 Development Team (Developers)
* **Core Focus:** Delivering a usable, high-quality product increment at the end of every sprint.
* **Responsibilities:**
  * Cross-functional group (developers, QA automation engineers, UI/UX designers, database admins) containing all skills needed to ship software.
  * Decides *how* to turn backlog items into functional software increments.
  * Self-organizes to estimate effort, assign tasks, and maintain high engineering quality.

---

### 2. Scrum Ceremonies: The Time-Boxed Events

Scrum uses four primary time-boxed ceremonies to create predictability, foster transparency, and inspect progress.

```text
                  ┌─────────────────────────────────────────────────┐
                  │              2 to 4-Week Sprint                 │
                  └────────────────────────┬────────────────────────┘
                                           │
 ┌─────────────────────────┐               │               ┌─────────────────────────┐
 │ 1. Sprint Planning      ├───────────────┼──────────────►│ 3. Sprint Review        │
 │ (Kickoff & Commitment)  │               │               │ (Demo & Feedback)       │
 └─────────────────────────┘               │               └─────────────────────────┘
                                           │
 ┌─────────────────────────┐               │               ┌─────────────────────────┐
 │ 2. Daily Scrum          ├───────────────┼──────────────►│ 4. Sprint Retrospective │
 │ (15-min Daily Alignment)│               │               │ (Process Improvement)   │
 └─────────────────────────┘               │               └─────────────────────────┘

```

🗓️ 1. Sprint Planning
* **When:** At the very start of a new sprint.

* **Duration:** Max 8 hours for a 1-month sprint (typically 2 hours for a 2-week sprint).

* **Objective:** The Product Owner presents high-priority items. The team collaborates to define the Sprint Goal and selects items from the Product Backlog to form the Sprint Backlog.

⚡ 2. Daily Scrum (Standup)
* **When:** Every single working day at the same time and place.

* **Duration:** Strictly 15 minutes.

* **Objective:** A quick operational alignment event for developers to inspect progress toward the Sprint Goal. Each member addresses:

    1. What did I complete yesterday that helped the team meet the Sprint Goal?

    2. What will I work on today to help the team meet the Sprint Goal?

    3. Are there any blockers or impediments stopping me?

🎨 3. Sprint Review (Demo)

* **When:** At the end of the sprint, before it closes.

* **Duration:** Max 4 hours for a 1-month sprint (typically 1 hour for a 2-week sprint).

* **Objective:** The team demonstrates the newly completed Increment directly to real business stakeholders to inspect working software, receive direct feedback, and adjust the Product Backlog for future sprints.

🔍 4. Sprint Retrospective
* **When:** Immediately following the Sprint Review, closing out the sprint.

* **Duration:** Max 3 hours for a 1-month sprint (typically 1 hour for a 2-week sprint).

* **Objective:** An internal-facing meeting focused on continuous process improvement. The team reflects on people, relationships, processes, and tools to answer:

    1. What went well during this sprint?

    2. What didn't go as expected?

    3. What concrete improvement will we commit to in the next sprint?

3. Scrum Artifacts & Commitments
Scrum Artifacts represent work or value. They are explicitly designed to maximize transparency of critical information across the team.

📜 1. Product Backlog
* **What it is:** An ordered, evolving list of features, enhancements, bug fixes, and technical requirements needed for the product. It is the single source of truth for work done by the team.

* **Associated Commitment:** Product Goal (The long-term target for the product).

📋 2. Sprint Backlog
* **What it is:** The subset of Product Backlog items chosen for the current sprint, paired with a concrete execution plan for delivering them.

* **Associated Commitment:** Sprint Goal (The single functional objective that provides business value during the sprint).

📦 3. The Increment
* **What it is:** The concrete, usable sum of all Product Backlog items completed during a sprint, combined with increments shipped in previous sprints. It must be in a fully working, deployable state.

* **Associated Commitment:** Definition of Done (DoD).

🏁 The Definition of Done (DoD)
The Definition of Done is an explicit, shared agreement among the Scrum team that defines the quality standards required before any backlog item can be declared a completed, shippable Increment.

It prevents technical debt and ensures that "Done" means fully completed, not "Done except for testing."

Enterprise Example: Definition of Done Checklist
A user story is only marked "Done" when it satisfies all of the following quality gates:

```Plaintext
[ ] Source Code complete and adheres to team style guidelines.
[ ] Code reviewed and approved by at least two peer developers via Pull Request.
[ ] Unit test suites written and passing with > 80% code coverage threshold.
[ ] Continuous Integration (CI) build pipeline executes successfully (Build: Green).
[ ] Integrated successfully into the staging environment with zero open regression bugs.
[ ] User acceptance criteria validated and signed off by the Product Owner.
[ ] API documentation and README user manuals updated accordingly.
```
---

## 📐 Module: Agile Estimation, Story Points & Planning Poker

In traditional software management, estimates were given in hours or days—a practice prone to inaccuracy because developers work at different speeds, encounter hidden technical complexity, and struggle to account for operational risks up front. Agile uses **relative estimation** to evaluate tasks based on overall effort, complexity, and risk rather than absolute time.

---

### 1. The Concept of Story Points

A **Story Point** is an abstract metric used in Agile software development to estimate the total effort required to implement a single backlog item or user story.

#### 🎯 What Comprises a Story Point?
Story points do not represent a fixed unit of time (e.g., 1 story point $\neq$ 8 hours). Instead, a story point is an integrated calculation of three core factors:

1. **Amount of Work (Volume):** How much actual coding, testing, and documentation needs to be created?
2. **Technical Complexity:** How difficult or intricate is the logic, system integration, or architectural design?
3. **Risk & Uncertainty:** Are there unknown dependencies, legacy code risks, or unclear third-party APIs involved?

#### 🔢 The Modified Fibonacci Scale
Instead of linear counting ($1, 2, 3, 4, 5, 6...$), Agile teams estimate using a **modified Fibonacci sequence**:

$$\mathbf{1,\ 2,\ 3,\ 5,\ 8,\ 13,\ 20,\ 40,\ 100}$$

* **Why exponential gaps?** As tasks grow larger, human capacity to accurately estimate fine details decreases. It is easy to distinguish between a small task ($2$) and a medium task ($3$), but virtually impossible to accurately distinguish between a $37$-point task and a $40$-point task. The widening gaps force teams to acknowledge uncertainty.
* **Large Numbers ($20, 40, 100$):** Indicate that a user story is too large, complex, or ambiguous to enter a sprint. It must be broken down into smaller stories during refinement.

---

### 2. Relative Estimation & Baseline Stories

Agile estimation works through **relative sizing**—comparing new work against a known reference point rather than estimating in isolation.

#### 🏋️ The "Dog Sizing" Analogy
Imagine trying to estimate the weight of a Labrador in exact kilograms off the top of your head. It is difficult. However, relative sizing is intuitive:
* A **Chihuahua** is small ($1$ point).
* A **Labrador** is clearly bigger than a Chihuahua ($5$ points).
* A **Great Dane** is significantly larger than a Labrador ($13$ points).

#### 🎯 Establishing a Baseline Story
1. The development team selects a simple, well-understood user story that everyone has worked on before (e.g., "Create a simple User Login form").
2. The team assigns this baseline story an arbitrary value, usually a **$2$ or $3$**.
3. All future user stories are estimated relative to this baseline: *"Is this new Payment API story twice as complex as our baseline ($5$), or four times as complex ($13$)?"*

---

### 3. The Planning Poker Technique

**Planning Poker** (also known as Scrum Poker) is a consensus-based estimation technique designed to prevent team bias, encourage active discussion, and ensure equal input from every team member.

#### 🃏 How Planning Poker Works (Step-by-Step)

```text
[ Product Owner Reads User Story ] 
              │
              ▼
    [ Technical Discussion ]
              │
              ▼
 [ Private Voting (Select Cards) ] ──► [ Simultaneous Reveal ]
                                                │
                                    ┌───────────┴───────────┐
                                    ▼                       ▼
                           (Agreement Reached)      (Divergence Detected)
                                    │                       │
                                    ▼                       ▼
                           [ Record Consensus ]   [ Extreme Voters Discuss ]
                                                            │
                                                            └──► [ Re-Vote Loop ]
```

1. Story Introduction: The Product Owner reads a user story aloud and explains the acceptance criteria.
2. Technical Discussion: Developers ask clarifying questions regarding architectural constraints, testing strategies, and dependencies.
3. Private Selection: Each developer independently selects a card representing their story point estimate from their deck ($1, 2, 3, 5, 8, 13...$) without revealing it to others.
4. Simultaneous Reveal: All team members flip their chosen cards face up at the exact same time.
5. Debate the Extremes:
     * If all cards match, consensus is reached, and the story points are recorded.
     * If estimates vary widely (e.g., votes are $2, 3, 3, 3,$ and $13$), the team does not simply average the numbers.
     * The team asks the highest voter ($13$) and the lowest voter ($2$) to explain their reasoning. The high voter might have caught a hidden security requirement, while the low voter might know an existing library already handles the feature.
6. Re-Vote: After brief discussion, the team votes again until consensus is reached.

💡 Key Benefits of Story Points & Planning Poker
* Prevents Anchoring Bias: Private voting prevents junior developers from simply echoing the estimate of the Tech Lead or most vocal engineer.
* Uncovers Hidden Complexity: Discussions highlight missing requirements or technical risks before sprint execution begins.
* Separates Estimation from Performance: Expressing work in story points removes the pressure of estimating time, allowing teams to measure historical Velocity (how many story points a team completes per sprint) accurately over time.

📊 Practical Example: Team Velocity Calculation
Once a team completes a few sprints, their Velocity stabilizes, allowing for accurate data-driven sprint planning:
| Sprint Iteration | Story Points Committed | Story Points Completed | 
| :--- | :--- | :--- |
| Sprint1 | 35 Points | 26 Points |
| Sprint2 | 30 Points | 28 Points |
| Sprint3 | 32 Points | 30 Points |
| Average Velocity | — | 28 Points / Sprint |


Planning Takeaway: During Sprint 4 planning, the team should commit to approximately 28 story points from the Product Backlog, pulling in stories based on their relative size.
---

## 📉 Module: Agile Planning Techniques – Sprint Planning, Velocity & Burndown Charts

Agile planning is an continuous, data-driven activity rather than a one-time upfront event. By combining structured **Sprint Planning** with metrics like **Velocity** and visual tracking tools like **Burndown Charts**, Scrum teams can reliably forecast releases and manage scope without burnout.

---

### 1. The Sprint Planning Process

**Sprint Planning** is the ceremonial event that initiates every new sprint. It establishes the operational scope and functional goals for the upcoming iteration.

#### 🎯 Inputs Required Before Planning Begins
*   A prioritized **Product Backlog** with refined user stories and acceptance criteria.
*   The team's historical **Velocity** metrics.
*   Team **Capacity** details (accounting for upcoming vacations, public holidays, or training).
*   The current **Increment** and technical architecture constraints.

#### 🛠️ The 3 Core Questions Answered in Sprint Planning

```text
┌───────────────────────────────────────────────────────────┐
│                   SPRINT PLANNING EVENT                   │
├─────────────────────────┬─────────────────────────────────┤
│ 1. WHY is this Sprint   │ Define the overarching          │
│    valuable?            │ SPRINT GOAL                     │
├─────────────────────────┼─────────────────────────────────┤
│ 2. WHAT can be Done     │ Select items from               │
│    this Sprint?         │ PRODUCT BACKLOG                 │
├─────────────────────────┼─────────────────────────────────┤
│ 3. HOW will the chosen  │ Deconstruct User Stories into   │
│    work be achieved?    │ SPRINT BACKLOG TASKS            │
└─────────────────────────┴─────────────────────────────────┘
```

1. Why is this Sprint valuable? The Product Owner proposes how the product can increase its value in this sprint. The entire Scrum Team collaborates to define a clear Sprint Goal.
2. What can be Done this Sprint? The developers pull top-priority user stories from the Product Backlog into the Sprint Backlog based on available team capacity and velocity.
3. How will the chosen work be achieved? For each selected item, developers break down the work into actionable technical tasks (e.g., database schema design, endpoint implementation, unit testing, UI wiring).

### 2. Team Velocity
Velocity is a metric that measures the amount of work a Scrum team successfully completes during a standard sprint iteration. It is calculated by summing the Story Points of all user stories that meet the team's official Definition of Done (DoD) by the end of the sprint.

⚠️ Important Rule: Partial points are never awarded for half-completed stories. If an 8-point story is 90% finished at sprint close, $0$ points count toward that sprint's velocity. The story carries over to the next sprint.

📊 Calculating Average VelocityTo calculate team velocity for future planning, average the completed points over the last 3 to 5 sprints:

$$\text{Average Velocity} = \frac{\sum (\text{Story Points Completed per Sprint})}{\text{Total Number of Sprints}}$$

Practical Example:
* Sprint 1: 22 points completed
* Sprint 2: 28 points completed
* Sprint 3: 25 points completed

$$\text{Average Velocity} = \frac{22 + 28 + 25}{3} = \mathbf{25\ \text{Points / Sprint}}$$

💡 How Velocity Guides Capacity PlanningVelocity provides a realistic data baseline to prevent overcommitment. If a team’s average velocity is $25$ points, they should pull approximately $25$ points worth of user stories into their next Sprint Backlog—not $40$.

3. Burndown Charts

A Burndown Chart is a visual tool that displays the amount of remaining work in a sprint on a daily basis. It gives teams immediate visibility into whether they are on track to achieve their Sprint Goal.

📉 Key Elements of a Sprint Burndown Chart
* Horizontal Axis (X-Axis): Represents time (the individual working days of the sprint).
* Vertical Axis (Y-Axis): Represents the total remaining effort (measured in Story Points or estimated hours).
* Ideal Guidelines Line: A straight diagonal reference line drawn from total initial story points on Day 1 down to zero on the final day of the sprint.
* Actual Progress Line: The actual daily tracking of remaining points as tasks meet the Definition of Done.

🔍 Interpreting Burndown Chart Trends

```Plaintext
Points
  ▲
30│ \ 
  │   \   <-- Ideal Guideline Line
20│────-●-- Actual Line ABOVE Ideal = Behind Schedule
  │      \
10│───────●-- Actual Line BELOW Ideal = Ahead of Schedule
  │        \
 0└─────────┴─────────┴─────────► Time (Days)
  Day 1    Day 5     Day 10
```

* Actual Line Above the Ideal Line: The team is behind schedule. Work is burning down slower than expected, indicating potential technical blockers, underestimated complexity, or scope creep.
* Actual Line Below the Ideal Line: The team is ahead of schedule. Work is being completed faster than anticipated, allowing the team to pull in additional refined items if capacity allows.
* Flat Line Across Multiple Days: Indicates an active blocker or non-working days where zero tasks are meeting the Definition of Done.

---

## 📝 Module: Agile User Stories & Acceptance Criteria

In Agile development, software requirements are not expressed as long, rigid specification documents. Instead, requirements are captured from the perspective of the end user in lightweight, plain-language descriptions known as **User Stories**. 

---

### 1. What is a User Story?

A **User Story** is an informal, general explanation of a software feature written from the perspective of the end user or customer. Its purpose is to articulate how a piece of work delivers actual business value back to the user.

User stories serve as conversation starters between the Product Owner, developers, and QA engineers, focusing on the *why* and *what* rather than the low-level technical *how*.

---

### 2. Standard User Story Template

To keep user stories user-centric and value-driven, teams follow a standard three-part format:

$$\text{\textbf{As a}} \ [\text{User Role}],\ \text{\textbf{I want}} \ [\text{Goal/Action}],\ \text{\textbf{so that}} \ [\text{Benefit/Value}]$$

#### 🔍 Deconstructing the Template Elements:
*   **As a `[User Role]`:** *Who are we building this for?* (e.g., Guest Visitor, System Admin, Account Holder).
*   **I want `[Goal/Action]`:** *What functionality or capability do they need to perform?*
*   **So that `[Benefit/Value]`:** *Why do they need this? What business problem does it solve?*

##### 💡 Basic Example:
> **As an** online shopper,  
> **I want to** save items to a persistent wishlist,  
> **so that** I can easily review and purchase them later when I am ready.

---

### 3. The INVEST Principle

To ensure user stories are high quality and ready to enter a sprint, teams evaluate them against the **INVEST** framework created by Bill Wake.

If a story fails to meet any of these criteria, it should be refined before sprint commitment:

```text
  I ──► Independent   (Minimal dependencies on other stories)
  N ──► Negotiable    (Leaves room for technical collaboration)
  V ──► Valuable      (Delivers clear, tangible benefit to end users)
  E ──► Estimable     (Clear enough for developers to size using Story Points)
  S ──► Small         (Fits comfortably within a single sprint iteration)
  T ──► Testable      (Has clear criteria to verify functionality)
```
* **I – Independent:** Stories should be decoupled so they can be developed, tested, and shipped in any order without blocking each other.

* **N – Negotiable:** Stories are not rigid contracts. The technical execution details are negotiated between developers and the Product Owner during planning.

* **V – Valuable:** Every story must yield measurable user or business value. Pure technical tasks should be framed around their end-value.

* **E – Estimable:** The team must understand the scope well enough to estimate its relative effort/complexity using Story Points.

* **S – Small:** Stories should be small enough to be completed within a few days, ensuring multiple stories can finish within a single sprint.

* **T – Testable:** A story must have clear verification rules so QA automation can confirm whether it works properly.

4. Acceptance Criteria & The Given-When-Then Format
While the user story defines the feature's goal, Acceptance Criteria define the boundary conditions. They are the explicit conditions that must be met for a user story to be declared completed and accepted by the Product Owner.

🥒 The Given-When-Then Structure (Behavior-Driven Development / BDD)
Writing criteria using the Given-When-Then format eliminates ambiguity and converts directly into automated test scripts (e.g., Cucumber or Behave).

* Given [`Initial Context/Precondition`]: Sets up the starting state of the system.

* When [`Action/Trigger Event`]: Describes the specific user interaction or event.

* Then [`Expected Result/Outcome`]: Defines the verifiable system response.

💻 Practical Blueprint: Complete User Story in Action
Here is an example showing how a raw feature request is converted into an actionable user story ready for sprint refinement:

Feature: Customer Password Reset Lifecycle
User Story Definition
```Plaintext
Title: Self-Service Password Reset

AS A registered bank user,
I WANT TO reset my forgotten password via an automated email link,
SO THAT I can regain access to my account securely without calling customer support.
```

Acceptance Criteria (Given-When-Then)
```Gherkin
Scenario 1: Successful Password Reset Link Request
  GIVEN a registered user is on the account login page
  WHEN they click "Forgot Password" and submit a valid, registered email address
  THEN the system sends a secure password reset email within 60 seconds
  AND displays a confirmation message: "Reset link dispatched to your inbox."

Scenario 2: Password Reset Link Expiration
  GIVEN a user receives a valid password reset email link
  WHEN they click the link more than 15 minutes after generation
  THEN the system displays an error message: "Security token expired."
  AND prompts them to request a new reset link.

Scenario 3: Non-Registered Email Handling (Security Rule)
  GIVEN a user is on the account login page
  WHEN they submit an email address that does NOT exist in the user database
  THEN the system displays the generic message: "If an account exists, a link has been sent."
  AND does NOT reveal whether the user email address exists in the system.
```
