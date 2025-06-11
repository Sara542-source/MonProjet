import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chatbot',
  template: `
    <div>
      <div id="voiceflow-chatbot"></div> <!-- Chatbot container -->
    </div>
  `,
  styles: [
    `
      #voiceflow-chatbot {
        position: fixed;
        bottom: 20px;
        right: 20px;
        z-index: 1000;
      }
    `,
  ],
})
export class ChatbotComponent implements OnInit {
  ngOnInit() {
    // Dynamically load Voiceflow script
    const randomuser='user_'+ Math.random().toString(36).substring(7);
    const script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://cdn.voiceflow.com/widget-next/bundle.mjs';
    script.onload = () => {
      // Initialize Voiceflow chatbot
      (window as any).voiceflow.chat.load({
        verify: { projectID: '683ae5d95e930b4a978a2cf0' }, // Replace with your projectID
        url: 'https://general-runtime.voiceflow.com',
        versionID: 'production',
        userId:randomuser,
        voice: {
          url: 'https://runtime-api.voiceflow.com',
        },
      });
    };
    document.body.appendChild(script); // Append script to the body
  }
}