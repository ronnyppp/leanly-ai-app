# 💬 Leanly AI

Leanly AI is an AI-powered fitness and wellness tracking Android application built to help users monitor workouts, nutrition, and daily habits while interacting with an intelligent AI coach for personalized guidance and progress insights.

## ✨ Features

- AI fitness coach powered by Gemini API for real-time conversations about workouts, nutrition, and wellness
  
- Offline support with persistent storage of users weight progress and conversations with Leanly AI coach

- Clean and responsive UI built with Jetpack Compose

- Structured and scalable architecture using MVVM

## 📸 Images
<p>
  <img width="250" alt="Screenshot_20260425_232640" src="https://github.com/user-attachments/assets/90573cad-01f5-44b8-a53b-21f452428069" />
  <img width="250" alt="Screenshot_20260425_232824" src="https://github.com/user-attachments/assets/71b58f16-0fed-4d8b-af96-3c70439b9b71" />
</p>

# 🚀 Running the project

## Clone the repo

git clone https://github.com/ronnyppp/leanly-ai-app.git

cd leanly-ai-app

## Firebase Setup

Go to [Firebase Console\(https://console.firebase.google.com)

Create a project

Register Android App (use com.example.talkieai for package name)

Download google-services.json

Place google-services.json inside of app folder

Go to AI Services then AI Logic

Click get started and choose Gemini Developer API

Enable API

Follow instructions in Firebase AI Logic to add SDK (if necesarry)

## Run the app

In terminal of IDE:

flutter pub get

flutter run
