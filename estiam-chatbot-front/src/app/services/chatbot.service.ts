import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs'; // ⚠️ Très important à importer

@Injectable({
  providedIn: 'root'
})
export class ChatbotService {
  private apiUrl = 'http://localhost:8080/api/chat';

  constructor(private http: HttpClient) {}

  async sendMessage(prompt: string): Promise<string> {
    try {
      const result = await firstValueFrom(
        this.http.post<{response: string}>(this.apiUrl, { prompt: prompt })
      );
      
      return result.response; 
      
    } catch (error) {
      console.error('Erreur attrapée dans le service :', error);
      return "Oups, je n'arrive pas à lire la réponse du serveur.";
    }
  }
}