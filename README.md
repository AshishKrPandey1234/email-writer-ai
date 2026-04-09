# 📧 AI Email Writer & Assistant

A sophisticated, full-stack AI ecosystem designed to streamline professional communication. This project leverages **Google Gemini AI** to generate context-aware, tone-specific email replies, integrated directly into the browser for a seamless user experience.

---

## 🚀 Key Features
* **AI-Powered Replies:** Intelligent response generation based on email content and user-selected tone (Professional, Casual, Friendly).
* **Gmail Integration:** A custom Chrome Extension that injects an "AI Reply" button directly into the Gmail interface.
* **Full-Stack Architecture:** A robust Spring Boot backend coupled with a modern React frontend.
* **Cross-Platform Utility:** Available as both a standalone web application and a browser extension.

---

## 📂 Project Structure
This repository is organized as a **Monorepo**:

* **`email-writer-sb/`**: The Backend service built with **Java 21** and **Spring Boot 3.x**. It handles API integration with Google Gemini, prompt engineering, and response processing.
* **`email-writer-react/`**: The Frontend dashboard built with **React.js** and **Material UI (MUI)** for a clean, responsive user interface.
* **`email-writer-extension/`**: The Chrome Extension (Manifest V3) that enables real-time integration with **Gmail** using Content Scripts and DOM manipulation.

---

## 🛠️ Tech Stack
* **Backend:** Java 21, Spring Boot, Spring WebFlux (WebClient), Jackson.
* **Frontend:** React.js, Vite, Material UI, Axios.
* **Extension:** JavaScript, HTML5, CSS3, Chrome Extension API (Manifest V3).
* **AI Integration:** Google Gemini 1.5 Flash API.
* **Tools:** Maven, Git, IntelliJ IDEA, VS Code.

---

## ⚙️ Setup & Installation

### Backend
1. Navigate to `/email-writer-sb`.
2. Add your Gemini API Key to `src/main/resources/application.properties`.
3. Run `./mvnw spring-boot:run`.

### Frontend
1. Navigate to `/email-writer-react`.
2. Run `npm install` and then `npm run dev`.

### Extension
1. Open Chrome and navigate to `chrome://extensions/`.
2. Enable **Developer Mode**.
3. Click **Load Unpacked** and select the `/email-writer-extension` folder.

---

## 📈 Technical Challenges & Learnings
* **CORS Configuration:** Implemented custom security headers in Spring Boot to allow cross-origin requests from Chrome Extension IDs.
* **DOM Manipulation:** Managed the injection of custom UI elements into a dynamic, complex web application like Gmail.
* **API Response Processing:** Built robust JSON parsing logic to extract clean text from multi-nested AI responses.

---

## 👤 Author
**Ashish Kumar Pandey**
* B.Tech in Computer Science and Engineering
* Galgotias University (Batch of 2027)
* [LinkedIn](https://www.linkedin.com/in/ashishkrpandey1234) | [GitHub](https://github.com/AshishKrPandey1234)
