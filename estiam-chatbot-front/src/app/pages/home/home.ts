import { Component, ChangeDetectorRef } from '@angular/core'; // ⬅️ 1. Ajoute ChangeDetectorRef ici
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatbotService } from '../../services/chatbot.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.html',
  styleUrls: ['./home.scss']
})
export class Home {
  messages: { text: string, sender: 'user' | 'bot' }[] = [];
  userInput: string = '';
  isLoading: boolean = false;

  constructor(
    private chatbotService: ChatbotService,
    private cdr: ChangeDetectorRef // ⬅️ 2. Injecte-le dans le constructeur
  ) {}

  async onSendMessage() {
    if (!this.userInput.trim()) return;

    const userMessage = this.userInput;
    this.messages = [...this.messages, { text: userMessage, sender: 'user' }];
    
    this.userInput = ''; 
    this.isLoading = true; 

    try {
      const botResponse = await this.chatbotService.sendMessage(userMessage);
      console.log("Réponse reçue :", botResponse); 
      
      this.messages = [...this.messages, { text: botResponse, sender: 'bot' }];
      
    } catch (error) {
      console.error("Erreur :", error);
      this.messages = [...this.messages, { text: "Oups, petit bug d'affichage.", sender: 'bot' }];
    } finally {
      this.isLoading = false; 
      
      // ⬅️ 3. LE COUP DE MARTEAU : Force la mise à jour du HTML quoi qu'il arrive !
      this.cdr.detectChanges(); 
    }
  }
}