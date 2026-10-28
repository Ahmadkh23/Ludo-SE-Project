# Software Development Plan (SDP)

## Educational Ludo Game System

**Group:** python<br>
**Version:** 1.0 — 28 October 2026<br>
**Submission date:** 01 November 2026<br>
**Presentation:** 02 November 2026<br>
**Planning horizon:** October–December 2026

This plan follows the applicable sections of IEEE Std 1058-1998 and Assignment 3. Format the export in Arial or David, 12 pt, 1.5 spacing, standard margins; the ten-page limit excludes cover and contents pages.

## 1. Overview

### 1.1 Project Summary

The Educational Ludo Game System is an offline Java Ludo game with course questions, history, and question administration for 2–4 players. The SRS is authoritative; [project requirements](docs/REQUIREMENTS.md) and [architecture](docs/ARCHITECTURE.md) describe the repository baseline.

#### 1.1.1 Purpose, Scope, and Objectives

The goal is a usable educational game and application of requirements engineering, MVC, iterative development, testing, patterns, and project management. Scope: setup, board, questions/surprises, results, history, and question administration; no network or online services.

Moodle history (`history.csv`) uses a persistence adapter for the course-defined columns and conventions. Compatibility requires verification against the authoritative schema and examples, currently awaited.

#### 1.1.2 Assumptions and Constraints

- Java 17; offline Windows 10/11 desktop; 2–4 local players; MVC required.
- Iteration 2 includes unit testing; Iteration 3 includes design patterns.
- Moodle CSV schema is authoritative but awaited; every iteration ends in an executable build; final release includes a JAR.

#### 1.1.3 Project Deliverables

1. GitHub repository and tagged release.
2. `SRS_python.pdf` v2.0, `prototype_python.pdf`, `SDP_python.pdf`.
3. Executable JAR, user manual, iteration test reports, and release notes.

#### 1.1.4 Schedule and Budget Summary

Three two-week iterations run 19 October–29 November; release preparation ends 13 December 2026. No paid labor or external-service costs. Submission: 1 November; presentation: 2 November.

### 1.2 Evolution of the SPMP

The Scrum Master maintains the baseline. Issues record rationale and requirement, schedule, risk, and deliverable impacts for Product Owner review. Approved changes are pull-requested, versioned, and recorded before implementation.

## 2. References

1. IEEE Std 1058-1998, *IEEE Standard for Software Project Management Plans*.
2. IEEE Std 830-1998, *IEEE Recommended Practice for Software Requirements Specifications*.
3. *Ludo Project – V2 – 2026* course brief; Assignment 3, `Exercise3SDP.pdf`.
4. Team SRS, `SRS_python.pdf`, v2.0.
5. Course Q&A, `ANSWERS.xlsx`.
6. Repository requirements and architecture, `docs/REQUIREMENTS.md`, `docs/ARCHITECTURE.md`.

The SRS PDF and Q&A workbook were not present in the workspace; requirement sub-ID wording and Q&A rulings (including SRS flexibility) therefore require confirmation against the course baselines before approval.

## 3. Definitions

| Term | Definition |
|---|---|
| Castle | Pieces' starting area. |
| Shared Track | Common route before each colour's home path. |
| Double Block | Two same-colour pieces block passage and landing. |
| Question Square | Triggers a question and its movement effect. |
| Surprise Square | Triggers a predefined random effect. |
| Iteration | Time-box ending in an integrated build. |
| Definition of Done (DoD) | Completion criteria in Section 5.3.4. |
| MVC | Model–View–Controller separation of rules, presentation, and flow. |
| Traceability | Link from SRS requirement to work, tests, and release. |

## 5. Managerial Process Plan

### 5.2 Work Plan

#### 5.2.1 Work Activities

The three iterations are two-week Scrum cycles. Each ends with an integrated, runnable build and a review against its user stories. The story counts below are planning estimates; story scope is refined during iteration planning without changing the SRS baseline.

| Iteration | Goal | Representative user stories |
|---|---|---|
| 1 — 19 Oct–1 Nov | Establish the project structure, initial MVC navigation, board rendering, dice rolling, token movement, and basic turn/rule flow. | As a player, I can start and configure a game; see the board and current turn; roll and move a legal piece; understand the current game phase. (5 stories) |
| 2 — 2–15 Nov | Complete rule enforcement, including Double Blocks and special squares; add JUnit 5 tests with a target of at least 80% line coverage; implement save/resume and the course CSV history mapping. | As a player, I can complete a rule-correct game, encounter special squares, and resume a saved game; as a course user, I can inspect correctly formatted history. (6 stories) |
| 3 — 16–29 Nov | Add Easy/Medium/Hard computer players, implement and document Strategy, Observer, and Factory patterns, complete question-bank administration, and harden the end-to-end system. | As a player, I can play against selectable AI difficulty; as an administrator, I can securely manage questions; as a user, I receive a stable, complete game and results. (6 stories) |

**Iteration 1 detailed task breakdown**

Requirement IDs below use the requested `FR-1.1`–`FR-12.2` and `NFR-01`–`NFR-11` identifier ranges. Their feature-level crosswalk is based on the project requirements currently available in `docs/REQUIREMENTS.md`; the authoritative SRS PDF was not present in the workspace at plan preparation. Before approving the SRS baseline, the Product Owner must verify the exact sub-ID-to-requirement wording and adjust these links if the SRS uses different assignments. This is a traceability verification action, not a change to the stated SRS identifier range.

| SRS Traceability ID | Requirement / User Story | Detailed Task | Assigned Executor (Role) |
|---|---|---|---|
| FR-1.1, FR-1.2, NFR-03 | As a player, I can enter the game through a clear main menu. | Define initial application structure and MVC package boundaries. | Ahmad Khawalid (Scrum Master / Lead Developer) |
| FR-2.1, FR-2.2 | As a player, I can configure 2–4 players and their names and colours. | Implement setup-screen navigation and validate player count, names, and unique colours. | Kasem Hilf (Product Owner / Developer) |
| FR-3.1, FR-3.2, NFR-01 | As a player, I can see the game board and pieces. | Define board and cell model, shared-track representation, and initial board rendering. | Mohamad Amara (Core Backend Developer) |
| FR-3.1, FR-3.2, NFR-03 | As a player, I can identify the current turn and player colours. | Implement board-screen layout and player/colour indicators. | Omar Ahmad (UI / Frontend Developer) |
| FR-4.1, FR-4.2 | As a player, I can roll the die on my turn. | Implement the initial dice operation and connect it to turn state. | Ahmad Khawalid (Scrum Master / Lead Developer) |
| FR-5.1, FR-5.2 | As a player, I can move a legal piece after a roll. | Implement token positions and the basic legal-move selection flow. | Mohamad Amara (Core Backend Developer) |
| FR-6.1, FR-6.2 | As a player, I can follow the basic turn and movement rules. | Enforce the Iteration 1 rule subset and reject invalid phase actions. | Kasem Hilf (Product Owner / Developer) |
| FR-1.1, FR-2.1, NFR-03 | As a user, I can navigate between the initial screens. | Wire menu, setup, and board navigation and handle back/cancel actions. | Omar Ahmad (UI / Frontend Developer) |
| FR-5.1, FR-6.1, NFR-10 | The team can verify the initial game flow. | Add focused checks for setup validation, dice flow, and legal movement. | Ahmad Khawalid (Scrum Master / Lead Developer) |
| FR-3.1, FR-5.1, NFR-03 | The board presentation reflects model state. | Connect board view refresh to controller actions without putting rules in the view. | Omar Ahmad (UI / Frontend Developer) |
| FR-1.2, NFR-09 | The build can be reproduced by the team. | Document build/run steps and establish the pull-request and review workflow. | Kasem Hilf (Product Owner / Developer) |
| FR-5.2, FR-6.2, NFR-10 | The iteration build is ready for demonstration. | Run acceptance walkthrough, record defects, and confirm the iteration DoD. | Mohamad Amara (Core Backend Developer) |

The role allocation rotates after Iteration 1. In Iteration 2, Ahmad is Product Owner, Kasem is Core Backend Developer, Mohamad is UI / Frontend Developer, and Omar is Scrum Master / Lead Developer. In Iteration 3, Ahmad is Core Backend Developer, Kasem is UI / Frontend Developer, Mohamad is Scrum Master / Lead Developer, and Omar is Product Owner. Technical ownership and peer-review responsibility are reassigned with the roles at each iteration kickoff.

#### 5.2.2 Schedule Allocation

| Dates | Milestone / deliverable | Acceptance evidence |
|---|---|---|
| 19–25 Oct | Iteration 1 planning, architecture skeleton, menu/setup, and board baseline | Stories assigned; executable navigation and board build |
| 26 Oct–1 Nov | Iteration 1 integration and review; Assignment 3 SDP and prototype submission | Demonstrable build; DoD and traceability review; prototype PDF due 1 Nov |
| 2–15 Nov | Iteration 2 rule completion, JUnit 5 suite, persistence, CSV work | Test report and acceptance of course-schema mapping |
| 16–29 Nov | Iteration 3 AI, design patterns, administration, system hardening | End-to-end demonstration and pattern/design review |
| 30 Nov–13 Dec | Final integration, cross-computer acceptance, documentation, release | Tagged source, final JAR, manual, and release notes |

### 5.3 Control Plan

#### 5.3.1 Requirements Control Plan

The Product Owner controls the SRS baseline and accepts stories. Tasks, pull requests, and test evidence cite SRS IDs. Requirement changes are assessed for scope, dependencies, schedule, risk, and test impact, then approved before baseline updates; unapproved scope stays in the backlog.

#### 5.3.4 Quality Control Plan — Iteration 1

**Definition of Done (DoD).** A task is complete only when:

- The code compiles with zero errors using Java 17.
- A local peer review is completed and requested changes are addressed.
- The responsible member commits the change to the GitHub task branch and opens or updates its pull request.
- Basic functionality is verified against the story's acceptance criteria.
- SRS traceability is recorded in the task and verification evidence.
- The integrated Iteration 1 build starts and the affected flow is demonstrated.

**Control intensity.** Formal peer review is used for model/rule logic, turn-state handling, controller-to-model integration, and build/release configuration because defects can invalidate play or the iteration build. Lightweight review is used for screen-navigation wiring, non-critical presentation details, and task documentation; a teammate checks the diff and acceptance behavior. All tasks still meet the DoD.

| Iteration 1 task group | Control level | Rationale |
|---|---|---|
| Board/model, legal movement, dice, turn flow (FR-3.x–FR-6.x) | Formal peer review | Core gameplay and state errors can block or corrupt the game; requirements are central to acceptance. |
| MVC wiring, initial screen navigation (FR-1.x–FR-2.x) | Lightweight review; formal review of controller boundary | Navigation is visible and testable; controller boundaries affect architecture and therefore receive closer review. |
| Build workflow and iteration integration (NFR-01, NFR-09, NFR-10) | Formal peer review | A broken build or unreproducible verification blocks demonstration and later integration. |
| Display player colour indicators on GUI (FR-2.2 / FR-3.2) | No deep review; visual smoke check only | This is the one intentionally unreviewed task: the change is presentational, low impact, and readily caught by visual testing; an error does not alter game state or rule enforcement. It still must compile and meet the DoD. |

### 5.4 Risk Management Plan

Probability and impact are rated High, Medium, or Low. Each risk has one named owner and one preventive and fallback action.

| ID / title | Cause → event → impact | Probability and impact | Mitigation and contingency | Owner |
|---|---|---|---|---|
| R1 — CSV schema unavailable | Course schema is delayed or ambiguous → history mapping cannot be verified → Moodle interoperability and acceptance are delayed. | Medium probability because the current requirements note says the schema is awaited; High impact because the integration format is externally defined. | Request and baseline the schema early; isolate mapping behind the history repository. If delayed, complete repository tests against a documented adapter contract and schedule a schema-specific integration build immediately on receipt. | Ahmad Khawalid |
| R2 — Iteration 1 scope exceeds capacity | Board, movement, UI, and initial rules are built in parallel → integration overruns → runnable iteration review is at risk. | Medium probability due to the breadth of the first playable flow; High impact because every iteration must end in a demonstrable build. | Prioritize a vertical playable slice and daily integration. If behind, defer nonessential visual polish and retain menu-to-board, roll, and legal movement. | Ahmad Khawalid |
| R3 — Rule interpretation mismatch | Ambiguous rules or board conventions are implemented inconsistently → acceptance tests disagree with expected play → rework affects core logic. | Medium probability because several board and movement edge cases require interpretation; High impact because rule correctness is central. | Record decisions against SRS clauses and review examples with the Product Owner. If a mismatch is found, revise the rule model and regression cases before adding features. | Kasem Hilf |
| R4 — Role rotation disrupts ownership | Iteration roles change without handover → work or decisions are lost → story completion and review slow down. | Medium probability because every iteration changes responsibilities; Medium impact because shared documentation and review can limit delay. | Use a short handover checklist, issue links, and documented decisions at each kickoff. If an owner is unavailable, the Scrum Master reassigns the task and records the change. | Kasem Hilf |
| R5 — Java/runtime variation | Developer or demo computers differ in Java setup or OS configuration → application fails to launch consistently → presentation or acceptance is blocked. | Medium probability because the team uses multiple computers; High impact if the demo build cannot start. | Build with Java 17 target and test on Windows 10/11 and a second computer. If launch issues occur, distribute the verified JAR with run instructions and diagnose from a clean runtime. | Mohamad Amara |
| R6 — JUnit coverage target missed | Test migration/integration is delayed or code is difficult to isolate → Iteration 2 coverage remains below 80% → course quality criterion is unmet. | Medium probability because the current source tree uses a lightweight custom test runner rather than JUnit 5; High impact because the course mandates JUnit in Iteration 2. | Decide the JUnit 5 integration path in Iteration 1 and measure line coverage continuously in Iteration 2. If the threshold is missed, prioritize uncovered rule branches and report the measured result rather than infer coverage. | Mohamad Amara |
| R7 — UI flow is unclear to first-time users | Navigation labels or modal transitions are not validated with users → users become stuck during the prototype or game → usability and demonstration quality decline. | Medium probability because interface decisions are being validated through a prototype; Medium impact because a facilitator can recover but user feedback is reduced. | Walk through the flow with another team and record observed issues. If users cannot proceed, simplify the path and provide a clear return/cancel action. | Omar Ahmad |
| R8 — GUI responsiveness degrades during play | Rendering, animation, or game work blocks the Swing event thread → input appears frozen → players perceive a crash. | Low probability for the initial board but Medium impact if introduced during later polish. | Keep model work bounded and use Swing event-thread rules; test interaction during representative turns. If a freeze occurs, disable or simplify animation and move expensive work off the UI thread where appropriate. | Omar Ahmad |

## 6. Technical Process Plan

### 6.1 Process Model

The team uses an Agile/Scrum adaptation with three two-week iterations, a planning session, brief regular coordination, backlog refinement, an iteration review, and a retrospective. Each iteration delivers an integrated runnable build. Roles rotate as defined in Section 5.2.1. GitHub Flow is used: one issue-linked feature branch per task, focused commits, pull request review, automated/manual checks, and merge to a buildable main branch.

MVC layer boundaries are:

- **Model:** board, players, pieces, game state, rules, and domain events; contains no Swing presentation logic.
- **View:** Swing screens and dialogs; presents state and collects input without changing game rules directly.
- **Controller:** translates user actions into model operations, coordinates navigation and persistence, and updates the view.

Persistence and question-bank services are accessed through defined interfaces so course-specific CSV and question storage details do not leak into game rules.

### 6.2 Methods, Tools, and Techniques

The implementation uses Java 17, Swing, Visual Studio Code, Git and GitHub, JUnit 5 for Iteration 2 unit tests, and Markdown for project documentation. Work is organized as issue-linked user stories with acceptance criteria and SRS traceability. Automated tests cover rule boundaries and persistence; manual walkthroughs verify screen flow, accessibility of controls, and final packaging. Iteration 3 uses and documents Strategy, Observer, and Factory patterns in the relevant design and code.

## 7. Supporting Process Plan

### 7.2 Verification and Validation Plan

Iteration 1 quality gates are defined in Section 5.3.4; they are not duplicated here. Across the project, verification includes requirement-to-test traceability, code review, automated unit and integration tests, CSV schema checks using course-provided examples, and build/release checks. Validation includes Product Owner acceptance against story criteria, first-time-user walkthroughs, representative games across difficulty levels, and confirmation that saved results and documentation meet course expectations. Before release, the team tests the executable on at least two computers and records the version, outcome, and defects. Iteration 2 reports measured line coverage against the ≥80% target.

### 7.3 Documentation Plan

The Scrum Master maintains the register and baselines. Authors prepare documents; named reviewers check accuracy and traceability; the Product Owner accepts course deliverables. Dates are planned, and baselines follow review. Distribution is through the team repository and course submission.

| Document Name | Template / Standard | Author | Reviewer | Review Date | Baseline Date | Distribution List |
|---|---|---|---|---|---|---|
| SRS, `SRS_python.pdf` v2.0 | IEEE 830-1998 and course SRS format | Kasem Hilf | Ahmad Khawalid; all members | 28 Oct 2026 | 30 Oct 2026 | Team, course instructor |
| SDP, `SDP_python.pdf` | IEEE 1058-1998; Assignment 3 format | Ahmad Khawalid | Kasem Hilf; all members | 30 Oct 2026 | 1 Nov 2026 | Team, course instructor |
| Prototype report, `prototype_python.pdf` | Assignment 3 prototype instructions | Omar Ahmad | All members | 31 Oct 2026 | 1 Nov 2026 | Team, course instructor |
| Code Javadoc | Java conventions and project package structure | All code authors | Assigned peer reviewer | At each pull request | Each accepted release | Team; maintainers |
| Meeting and iteration minutes | Team decision/action log | Rotating Scrum Master | Product Owner | At each iteration review | At each iteration review | Team |
| Test reports | JUnit 5 output, coverage report, and acceptance checklist | Test/task owner for each story | Peer reviewer; Scrum Master | End of each iteration | End of each iteration | Team, course instructor on request |
| Release notes | GitHub release-note format and SRS change links | Rotating Scrum Master | Product Owner | 13 Dec 2026 | 13 Dec 2026 | Team, course instructor, users |
| User manual | Project user-guide structure | Omar Ahmad | Kasem Hilf; all members | 10 Dec 2026 | 13 Dec 2026 | Team, course instructor, players |

## 4.2 Team Members' Work Breakdown

This table identifies authorship of the SDP sections, separate from implementation task ownership. Each author drafts the assigned sections; the designated reviewer checks them before the Scrum Master baselines the document.

| Team member | SDP sections authored |
|---|---|
| Ahmad Khawalid (212865513) | 1.2 Evolution of the SPMP; 5.3 Control Plan; 6.1 Process Model |
| Kasem Hilf (213031289) | 1.1 Project Summary and subsections; 2 References; 3 Definitions; requirements-control content in 5.3.1 |
| Mohamad Amara (214578007) | 5.2 Work Plan, iteration activities, and schedule allocation; 6.2 Methods, Tools, and Techniques |
| Omar Ahmad (212279632) | 5.4 Risk Management Plan; 7.2 Verification and Validation Plan; 7.3 Documentation Plan |

All four members review the complete SDP. The Scrum Master incorporates approved edits, checks section/requirement traceability, and records the final baseline revision.
