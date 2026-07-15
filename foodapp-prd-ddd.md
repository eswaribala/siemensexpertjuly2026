# FoodHub — Restaurant Discovery & Food Delivery Platform
### Product Requirements Document & Domain-Driven Design
*(Zomato / Swiggy-style platform — React frontend, polyglot backend)*

> **Stack assumption:** Since "Python / Spring Boot" spans two ecosystems, this document uses a **polyglot microservices architecture**: Java + Spring Boot for core transactional services (orders, payments, restaurants, delivery), and Python (FastAPI) for search, recommendations, ML/fraud, and analytics — the pattern real platforms like Swiggy/Zomato actually use. Adjust the language-to-service mapping in Section 5 if you intended something different.

---

## 1. Executive Summary

FoodHub is a three-sided marketplace connecting **customers**, **restaurants**, and **delivery partners**. Customers discover restaurants, browse menus, place orders, and pay online; restaurants manage menus and fulfill orders; delivery partners pick up and deliver food; the platform earns commission, delivery fees, and ad/promotion revenue.

**Goals:** sub-3-second discovery-to-cart flow, <30 min average delivery time, 99.9% platform uptime, horizontally scalable to 10M+ orders/day.

---

## 2. Stakeholders & Personas

| Persona | Needs |
|---|---|
| **Customer** | Find good food fast, track order live, reliable ETAs, easy re-order, offers |
| **Restaurant Owner/Manager** | Manage menu & inventory, receive orders, control online/offline status, view payouts & analytics |
| **Delivery Partner** | Get nearby order requests, optimal routing, earnings tracking, in-app navigation |
| **Platform Admin/Ops** | Onboard restaurants, handle disputes, monitor fraud, manage commissions, city-level ops |
| **Business/Finance** | Settlement, reconciliation, commission reports, tax invoicing (GST) |

---

## 3. Product Requirements (Functional)

### 3.1 Customer App/Web
- FR1: Location-based restaurant discovery (geo search, filters: cuisine, rating, price, delivery time, veg/non-veg, offers)
- FR2: Search with autosuggest (dish, restaurant, cuisine) with typo tolerance
- FR3: Restaurant detail page — menu, photos, reviews, FSSAI/hygiene rating, timings
- FR4: Cart management — item customization (add-ons, variants, special instructions), multi-item combos
- FR5: Checkout — address selection/geo-pin, delivery instructions, payment method (UPI, card, wallet, COD, net banking)
- FR6: Coupon/promo code application, platform loyalty points redemption
- FR7: Order placement → real-time order status (placed → accepted → preparing → picked up → out for delivery → delivered)
- FR8: Live order tracking on map with delivery partner location & ETA
- FR9: Order history, re-order, invoice download
- FR10: Ratings & reviews (restaurant + delivery experience), photo uploads
- FR11: Customer support / order issue reporting → refund/replacement workflow
- FR12: Push/SMS/email notifications at each order stage
- FR13: Favorites, saved addresses, multiple profiles

### 3.2 Restaurant Partner App/Portal
- FR14: Onboarding — business details, FSSAI license, bank details, menu upload (bulk CSV/manual)
- FR15: Menu & inventory management — item availability toggle, price updates, out-of-stock marking
- FR16: Incoming order dashboard — accept/reject with reason, preparation time input
- FR17: Order fulfillment status updates (preparing, ready for pickup)
- FR18: Sales analytics dashboard — daily/weekly revenue, top items, peak hours
- FR19: Payout/settlement statements, commission breakdown
- FR20: Promotions management — self-serve discounts, featured listing purchase

### 3.3 Delivery Partner App
- FR21: Online/offline toggle, current location broadcast
- FR22: Order assignment notification (accept/reject within timeout)
- FR23: Turn-by-turn navigation to restaurant, then to customer
- FR24: OTP-based delivery confirmation / proof of delivery
- FR25: Earnings dashboard, incentive tracking, daily payout summary

### 3.4 Admin/Ops Console
- FR26: Restaurant approval workflow, catalog moderation
- FR27: Order dispute/refund management
- FR28: Fraud & abuse monitoring (fake orders, delivery partner collusion)
- FR29: Dynamic commission & surge-fee configuration by city/zone
- FR30: City/zone-level operational dashboards (order volume, SLA breaches)

---

## 4. Non-Functional Requirements

| Category | Requirement |
|---|---|
| **Performance** | Search P95 < 300ms; Order placement P95 < 1s; homepage TTFB < 500ms |
| **Scalability** | Horizontally scalable microservices; handle 10x traffic during lunch/dinner peaks |
| **Availability** | 99.9% uptime for order path; graceful degradation (cached menus) during outages |
| **Consistency** | Strong consistency for payments/inventory; eventual consistency for search/ratings |
| **Security** | OAuth2/JWT auth, PCI-DSS scope isolation for payments, encryption at rest/in transit, rate limiting |
| **Observability** | Distributed tracing (OpenTelemetry), centralized logging (ELK), metrics (Prometheus/Grafana) |
| **Compliance** | GST invoicing, FSSAI display, data privacy (DPDP/GDPR-style consent) |
| **Geo-scale** | Multi-region deployment, city-sharded delivery-matching |

---

## 5. High-Level System Architecture

```
                         ┌───────────────────────────┐
                         │      React Web / RN App    │
                         └──────────────┬─────────────┘
                                        │ HTTPS
                         ┌──────────────▼─────────────┐
                         │   API Gateway (Spring Cloud │
                         │   Gateway / Kong) + Auth    │
                         └──────────────┬─────────────┘
              ┌────────────┬────────────┼────────────┬──────────────┐
              ▼            ▼            ▼            ▼              ▼
        ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌───────────┐ ┌─────────────┐
        │ Identity │ │ Catalog/ │ │ Ordering │ │  Payment  │ │  Delivery/  │
        │  (Java)  │ │  Menu    │ │  (Java)  │ │  (Java)   │ │  Logistics  │
        │          │ │  (Java)  │ │          │ │           │ │  (Java)     │
        └──────────┘ └──────────┘ └──────────┘ └───────────┘ └─────────────┘
              ▼            ▼            ▼            ▼              ▼
        ┌──────────┐ ┌──────────┐ ┌──────────────┐ ┌────────────┐ ┌───────────┐
        │  Search  │ │  Reco/   │ │ Notification │ │  Fraud/ML  │ │ Analytics │
        │ (Python  │ │ Ranking  │ │  (Java/Go)   │ │  (Python)  │ │ (Python)  │
        │  FastAPI)│ │ (Python) │ │              │ │            │ │           │
        └──────────┘ └──────────┘ └──────────────┘ └────────────┘ └───────────┘

        Event Backbone: Apache Kafka  |  Cache: Redis  |  Search Index: Elasticsearch
        DB-per-service: PostgreSQL (transactional), MongoDB (catalog), Cassandra (location pings)
```

**Rationale for language split:**
- **Java/Spring Boot** — services needing strong transactional guarantees, mature ORM/tooling, high-throughput synchronous APIs: Identity, Catalog, Ordering, Payment, Delivery-matching.
- **Python/FastAPI** — services that are compute/ML-heavy or benefit from the data-science ecosystem: Search relevance, restaurant/dish recommendations, fraud scoring, demand forecasting, analytics pipelines (often backed by Spark/pandas).
- Cross-language communication happens only via **Kafka events** or **well-defined REST/gRPC contracts** — never shared databases.

---

## 6. Domain-Driven Design

### 6.1 Core Domain vs Supporting vs Generic

| Type | Bounded Contexts |
|---|---|
| **Core Domain** (competitive differentiation) | Ordering, Delivery Logistics & Dispatch, Search & Discovery, Recommendation |
| **Supporting Domain** | Catalog/Menu Management, Restaurant Onboarding, Ratings & Reviews, Promotions |
| **Generic Domain** | Identity & Access, Payment Processing, Notification, Analytics/Reporting |

### 6.2 Bounded Contexts & Ubiquitous Language

#### A. Identity & Access Context
- **Ubiquitous language:** User, Role (Customer/RestaurantOwner/DeliveryPartner/Admin), Session, Credential
- **Aggregate Root:** `User`
- **Entities:** `Address`, `Device`
- **Value Objects:** `PhoneNumber`, `Email`, `GeoCoordinate`
- **Domain Events:** `UserRegistered`, `UserVerified`, `AddressAdded`

#### B. Restaurant & Catalog Context
- **Ubiquitous language:** Restaurant, Menu, MenuItem, Variant, AddOn, Availability, Cuisine
- **Aggregate Root:** `Restaurant` (owns `Menu` as part of aggregate boundary; `MenuItem` is child entity)
- **Entities:** `MenuItem`, `MenuSection`
- **Value Objects:** `Price`, `PreparationTime`, `FssaiLicense`, `OperatingHours`
- **Domain Events:** `RestaurantOnboarded`, `MenuItemPriceChanged`, `ItemMarkedOutOfStock`, `RestaurantOpenedForOrders`, `RestaurantClosedForOrders`
- **Invariants:** A restaurant cannot accept orders outside `OperatingHours`; out-of-stock items cannot be added to cart (enforced via cached read model, reconciled by event).

#### C. Ordering Context (Core Domain)
- **Ubiquitous language:** Cart, Order, OrderLine, OrderStatus, Coupon
- **Aggregate Root:** `Order`
- **Entities:** `OrderLine`
- **Value Objects:** `Money`, `DeliveryAddress`, `OrderStatus` (enum: PLACED, ACCEPTED, PREPARING, READY, PICKED_UP, DELIVERED, CANCELLED)
- **Domain Events:** `OrderPlaced`, `OrderAcceptedByRestaurant`, `OrderRejectedByRestaurant`, `OrderReadyForPickup`, `OrderPickedUp`, `OrderDelivered`, `OrderCancelled`
- **Invariants:** An `Order` total must equal sum of `OrderLine` totals + fees − discounts; status transitions follow a strict state machine (no skipping from PLACED→DELIVERED).
- **Note:** `Cart` is a separate, ephemeral aggregate (Redis-backed) that converts into an immutable `Order` at checkout — this separation avoids polluting the Order aggregate with pre-checkout mutability.

#### D. Payment Context (Generic, PCI-isolated)
- **Ubiquitous language:** Payment, Transaction, Refund, Settlement, Wallet
- **Aggregate Root:** `Payment`
- **Value Objects:** `Money`, `PaymentMethod`, `TransactionReference`
- **Domain Events:** `PaymentAuthorized`, `PaymentCaptured`, `PaymentFailed`, `RefundIssued`
- **Invariants:** `Payment` is idempotent per `OrderId` (dedup via idempotency key); capture only after `OrderPlaced` and before restaurant acceptance timeout.

#### E. Delivery & Logistics Context (Core Domain)
- **Ubiquitous language:** DeliveryPartner, Assignment, Route, Pickup, DropOff, ETA
- **Aggregate Root:** `DeliveryAssignment`
- **Entities:** `DeliveryPartner` (own aggregate for partner state), `Route`
- **Value Objects:** `GeoCoordinate`, `ETA`, `DistanceKm`
- **Domain Events:** `PartnerAssigned`, `PartnerReachedRestaurant`, `OrderPickedUp`, `PartnerReachedCustomer`, `DeliveryCompleted`, `AssignmentReassigned` (on partner rejection/timeout)
- **Invariants:** One active assignment per order at a time; reassignment triggers within N seconds of no partner acceptance.

#### F. Search & Discovery Context (Core Domain, Python)
- **Ubiquitous language:** SearchQuery, SearchResult, Facet, RankingScore
- **Aggregate Root:** none (read-heavy, largely a query-side/CQRS projection over Catalog + Ratings events)
- **Domain Events consumed:** `RestaurantOnboarded`, `MenuItemPriceChanged`, `ReviewSubmitted` → re-index into Elasticsearch

#### G. Recommendation Context (Python)
- Consumes order history, browsing events, and produces `RecommendedRestaurants`/`RecommendedDishes` read models per user — pure supporting/analytical context, no write-side invariants of its own.

#### H. Ratings & Reviews Context
- **Aggregate Root:** `Review`
- **Value Objects:** `Rating` (1–5), `ReviewText`
- **Domain Events:** `ReviewSubmitted`, `ReviewFlagged`
- **Invariants:** One review per (Customer, Order); review only allowed after `OrderDelivered`.

#### I. Notification Context (Generic)
- Subscribes to domain events across contexts and fans out to Push/SMS/Email — stateless orchestration, no domain aggregates.

### 6.3 Context Map

| Relationship | Pattern |
|---|---|
| Ordering → Catalog | **Customer/Supplier** — Ordering reads a denormalized menu snapshot at order time (price-lock), doesn't call Catalog synchronously on every checkout |
| Ordering → Payment | **Conformist-ish / Anti-Corruption Layer** — Ordering defines its own `PaymentRequest` DTO, translated at the boundary |
| Ordering → Delivery | **Published Language** via Kafka (`OrderReadyForPickup` event triggers dispatch) |
| Delivery → Identity | **Shared Kernel** for partner identity only (minimal shared `PartnerId`, `GeoCoordinate` VO) |
| Search/Recommendation → Catalog/Ratings/Ordering | **Event-driven read-model projection** (CQRS) — no direct synchronous coupling |
| All contexts → Notification | **Open Host Service** — Notification exposes a generic event-consumption contract |

### 6.4 Strategic Pattern: CQRS + Event Sourcing (selective)
- **Ordering context** uses event sourcing for the `Order` aggregate (full audit trail needed for disputes/refunds) — event store (Kafka + compacted topic or dedicated event DB), with a PostgreSQL read-model projection for queries.
- **Catalog, Search, Recommendation** are pure CQRS read-side consumers, no event sourcing needed on their own write side.

---

## 7. Key Use-Case Flow — Order Placement (Sequence)

1. Customer app → **API Gateway** → `POST /cart/checkout` (Ordering Service, Java)
2. Ordering Service validates cart against **cached Catalog read-model** (price, availability)
3. Ordering Service → **Payment Service**: `authorize(orderId, amount, method)` (sync, gRPC)
4. On success → Ordering emits `OrderPlaced` (Kafka)
5. **Restaurant Service** consumes `OrderPlaced` → pushes to restaurant dashboard (WebSocket) for accept/reject
6. Restaurant accepts → emits `OrderAcceptedByRestaurant` → Payment Service **captures** the authorized amount
7. On `OrderReadyForPickup` → **Delivery Service** runs matching algorithm (nearest available partner, ETA-optimized) → emits `PartnerAssigned`
8. Delivery Partner app receives push → accepts → `PartnerReachedRestaurant` → `OrderPickedUp`
9. Customer app subscribes to order-status **WebSocket/SSE channel** for live tracking
10. `DeliveryCompleted` → Notification Service sends delivery confirmation + prompts rating
11. Nightly batch (Python/Spark) reconciles settlements → restaurant + delivery partner payouts

---

## 8. Data Model Highlights (per-service databases)

| Service | Store | Key Tables/Collections |
|---|---|---|
| Identity | PostgreSQL | users, addresses, roles, sessions |
| Catalog | MongoDB | restaurants, menus (nested items — matches read pattern) |
| Ordering | PostgreSQL + event store | orders, order_lines, order_events |
| Payment | PostgreSQL (PCI-scoped, isolated network) | payments, refunds, settlements |
| Delivery | PostgreSQL + Redis (live geo) | delivery_partners, assignments; Redis geo-index for live matching |
| Search | Elasticsearch | restaurant_index, dish_index |
| Ratings | PostgreSQL | reviews |
| Location pings | Cassandra/TimescaleDB | high-write-volume partner GPS trail |

---

## 9. Technology Stack Summary

| Layer | Technology |
|---|---|
| Frontend Web | React 18, Redux Toolkit/Zustand, React Query, TypeScript, Tailwind CSS, Mapbox/Google Maps SDK, Socket.io/SSE client |
| Mobile | React Native (shared component logic with web where feasible) |
| API Gateway | Spring Cloud Gateway or Kong, JWT/OAuth2 validation |
| Core Services | Java 17, Spring Boot 3, Spring Data JPA, Spring Security, gRPC (inter-service), Resilience4j (circuit breakers) |
| ML/Search Services | Python 3.12, FastAPI, Elasticsearch client, scikit-learn/PyTorch for recommendation models, Celery for async jobs |
| Messaging | Apache Kafka (event backbone), Redis Streams (lightweight cases) |
| Caching | Redis (session, cart, geo-index) |
| Search | Elasticsearch |
| Databases | PostgreSQL (transactional), MongoDB (catalog), Cassandra/TimescaleDB (location time-series) |
| Infra | Docker, Kubernetes (EKS/GKE), Istio (service mesh), Terraform |
| Observability | Prometheus + Grafana, ELK/OpenSearch, OpenTelemetry + Jaeger |
| CI/CD | GitHub Actions/Jenkins, ArgoCD (GitOps) |
| Payments | Razorpay/Stripe-style PSP integration, PCI-DSS scoped subnet |

---

## 10. Non-Functional Deep Dives

- **Delivery-matching at scale:** partner locations held in Redis Geo (GEOADD/GEORADIUS) for sub-100ms nearest-partner queries per city shard.
- **Resilience:** circuit breakers (Resilience4j) on all inter-service calls; Ordering degrades to "restaurant busy" messaging if Delivery Service is down rather than failing the whole order.
- **Idempotency:** all write APIs (`checkout`, `capture`, `assign`) require an idempotency key to survive client retries/network blips.
- **Multi-tenancy/city sharding:** Delivery and Search services partition by city/zone for both data locality and independent scaling during regional peak hours.

---

## 11. Roadmap (Phased Delivery)

| Phase | Scope |
|---|---|
| **Phase 1 — MVP** | Identity, Catalog, Ordering, Payment (single PSP), basic Delivery assignment (manual/nearest), React web app, single-city |
| **Phase 2 — Scale** | Search & Discovery service, live tracking (WebSocket), Ratings, Notification service, multi-city |
| **Phase 3 — Intelligence** | Recommendation engine, dynamic pricing/surge, fraud detection (Python/ML), analytics dashboards |
| **Phase 4 — Growth** | Promotions/ads platform, loyalty program, restaurant self-serve analytics, third-party logistics API |

---

## 12. Success Metrics (KPIs)

- Order conversion rate (search → order)
- Average delivery time & on-time delivery %
- Customer retention / repeat-order rate
- Restaurant churn rate
- GMV (Gross Merchandise Value) growth
- Delivery partner utilization rate
- Platform uptime / P95 latency per critical API

---

*This document is a starting architecture blueprint. Recommended next steps: validate bounded-context boundaries with domain experts (event-storming workshop), define OpenAPI/gRPC contracts per service, and stand up Phase 1 MVP services.*
