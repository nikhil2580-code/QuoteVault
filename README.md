
📱 QuoteVault — AI-Powered Quote Discovery App

QuoteVault is a full-featured quote discovery and collection mobile application built as part of the Mobile Application Developer Assignment.
The goal of this project was not only to build a functional app, but also to demonstrate effective usage of AI tools to accelerate development, improve code quality, and solve problems efficiently.

🚀 Tech Stack

Platform: Android

Language: Kotlin

UI: XML + Material Design

Backend: Supabase (Auth + Database)

Architecture: MVVM

Async: Kotlin Coroutines

Image & UI Assets: Stitch / Figma Make

Version Control: GitHub

🔐 Features Implemented
1. Authentication & User Accounts

Email & password sign-up

Login / logout

Session persistence (user stays logged in)

Password reset via Supabase

Basic user profile support

Tech: Supabase Auth

2. Quote Browsing & Discovery

Home feed displaying quotes

Browse quotes by categories:

Motivation

Love

Success

Wisdom

Humor

Search quotes by keyword

Pull-to-refresh

Loading & empty states handled

Tech: Supabase Database (quotes table seeded with 100+ quotes)

3. Favorites & Collections

Save quotes to favorites

View all favorited quotes

Favorites synced to user account

Persistent cloud storage

Tech: Supabase Database

4. Daily Quote

“Quote of the Day” shown on Home screen

Daily quote changes using local logic

Prepared structure for notifications

5. Sharing

Share quotes as text using system share sheet

UI prepared for shareable quote cards

6. Personalization

Light / Dark mode support

User preferences stored locally

🧠 AI Coding Approach & Workflow 

This project was built with AI-assisted development as a first-class workflow, exactly as requested in the assignment.

How I Used AI Effectively

Feature Planning

Broke down the assignment into small, testable modules using ChatGPT.

Prioritized core features: Auth → Quotes → Favorites → Sharing.

Code Generation

Used AI to:

Generate Supabase Auth integration

Create MVVM structure

Write RecyclerView adapters

Handle edge cases and error states

Debugging & Problem Solving

Used AI to debug:

Supabase session persistence

BuildConfig & environment variable issues

Lifecycle and navigation bugs

Compared multiple AI suggestions and applied best practices.

UI Development

Generated base UI layouts using Stitch 

Converted designs into XML layouts with AI assistance

Manually refined spacing, typography, and consistency

Documentation

Used AI to structure README clearly and professionally

Documented limitations honestly (as requested)

🤖 AI Tools Used

ChatGPT – architecture design, debugging, Supabase integration, documentation

Google Stitch – UI design generation

Android Studio AI tools – code completion & refactoring

(No AI usage is hidden — AI was used intentionally and transparently.)

🎨 Design Resources

Design Tool: Stitch / Figma Make

Design Link: https://stitch.withgoogle.com/projects/549464633444820483

UI follows a clean, minimal, and readable design language with consistent spacing and typography.

⚙️ Setup Instructions (Supabase Configuration)
1. Clone Repository
git clone  https://github.com/nikhil2580-code/QuoteVault

2. Create Supabase Project

Go to https://supabase.com

Create a new project

Enable Auth (Email/Password)

3. Database Setup

Create tables:

quotes

favorites

users

Seed the quotes table with 100+ quotes across 5 categories.

4. Supabase Keys

Add your Supabase credentials using local.properties (not committed):

SUPABASE_URL=your_supabase_url
SUPABASE_ANON_KEY=your_anon_key


The app reads these securely via BuildConfig.

⚠️ Known Limitations / Incomplete Features

Widget implementation is not completed

Daily notification scheduling is partially implemented

Shareable quote image templates are limited

Advanced animations and transitions can be improved

Collections feature is basic (can be extended)

These limitations are documented intentionally, as quality and transparency were prioritized.


📌 Final Notes

This project demonstrates:

Strong fundamentals in Android development

Real-world backend integration using Supabase

Effective, transparent, and intentional AI usage

Clean structure and readable code
![img1](https://github.com/user-attachments/assets/72f56483-461e-42c8-a872-acdf53528f3f)
![img2](https://github.com/user-attachments/assets/c05af13a-0cd6-4d2c-b0aa-2e60030f1a5d)
![img3](https://github.com/user-attachments/assets/18eb01c1-bf00-4501-9823-f5184c43da6f)
![img4](https://github.com/user-attachments/assets/1ef8fc12-3ac8-426f-8560-43afd7cb2e4d)
![img5](https://github.com/user-attachments/assets/c13e8fa4-78ef-4d76-bcfb-5b3b2964bf6c)
![img6](https://github.com/user-attachments/assets/9bf20945-ade2-4884-8bf3-b3bda09c0022)
![IMG_20260130_135442](https://github.com/user-attachments/assets/d268398b-dac1-4391-a19d-476a485c764c)

