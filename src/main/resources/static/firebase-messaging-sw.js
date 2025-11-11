importScripts(
  "https://www.gstatic.com/firebasejs/10.8.1/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/10.8.1/firebase-messaging-compat.js"
);

firebase.initializeApp({
  apiKey: "AIzaSyDL_qwuNdSMdlIcZTI0M6sVk1RNHl9x4Zo",
  authDomain: "exercisesadvancernapp--hcltech.firebaseapp.com",
  projectId: "exercisesadvancernapp--hcltech",
  storageBucket: "exercisesadvancernapp--hcltech.firebasestorage.app",
  messagingSenderId: "878401816142",
  appId: "1:878401816142:web:47390f85f96f4241a4e51f",
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log("📩 Received background message:", payload);
  const { title, body } = payload.notification || {};
  self.registration.showNotification(title, {
    body,
    icon: "/firebase-logo.png",
  });
});
