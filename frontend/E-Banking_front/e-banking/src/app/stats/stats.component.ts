import { Component, OnInit } from '@angular/core';
import { ChartConfiguration, ChartType, ChartData, ChartOptions } from 'chart.js';
import { AuthService } from '../services/auth.service';
import { AccountService, Account } from '../services/account.service';
import { TransactionService, Transaction } from '../services/transaction.service';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css'],
  standalone: true,
  imports: [CommonModule, NgChartsModule]
})
export class StatsComponent implements OnInit {
  chartData: ChartData<'line'> = { labels: [], datasets: [] };
  chartOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: { legend: { display: true } },
    scales: {
      y: {
        beginAtZero: true,
        max: 10, // <-- Limite l’axe Y à 15
        ticks: { stepSize: 1 }
      }
    }
  };
  chartType: 'line' = 'line';

  accounts: Account[] = [];
  transactions: Transaction[] = [];
  errorMessage = '';
  colorPalette = ['#3366CC', '#DC3912', '#FF9900', '#109618', '#990099', '#0099C6', '#DD4477'];

  constructor(
    private authService: AuthService,
    private accountService: AccountService,
    private transactionService: TransactionService
  ) {}

  ngOnInit(): void {
    const client = this.authService.getCurrentClient();
    if (client && client.clientId) {
      this.accountService.getAccountsByClientId(client.clientId).subscribe({
        next: (accounts) => {
          this.accounts = accounts;
          this.transactionService.getTransactionsByClientId(client.clientId).subscribe({
            next: (transactions) => {
              this.transactions = transactions;
              this.prepareChartData();
            },
            error: () => this.errorMessage = 'Erreur lors du chargement des transactions'
          });
        },
        error: () => this.errorMessage = 'Erreur lors du chargement des comptes'
      });
    } else {
      this.errorMessage = "Impossible de récupérer l'utilisateur connecté";
    }
  }

  parseBackendDate(arr: number[] | null): Date | null {
    if (!arr) return null;
    return new Date(
      arr[0], arr[1] - 1, arr[2], arr[3] || 0, arr[4] || 0, arr[5] || 0, Math.floor((arr[6] || 0) / 1_000_000)
    );
  }

  prepareChartData() {
    if (!this.accounts.length || !this.transactions.length) return;

    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const labels = Array.from({ length: daysInMonth }, (_, i) => (i + 1).toString());

    const datasets = this.accounts.map((account, idx) => {
      const data = new Array(daysInMonth).fill(0);

      const accountTransactions = this.transactions.filter(
        t => (t.senderAccount && t.senderAccount.id === account.id) ||
             (t.receiverAccount && t.receiverAccount.id === account.id)
      );

      for (const t of accountTransactions) {
        let date: Date | null = null;
        if (Array.isArray(t.date)) {
          date = this.parseBackendDate(t.date);
        } else if (t.date) {
          date = new Date(t.date);
        }
        if (
          date &&
          date.getFullYear() === year &&
          date.getMonth() === month
        ) {
          data[date.getDate() - 1]++;
        }
      }

      return {
        label: account.accountNumber,
        data,
        borderColor: this.colorPalette[idx % this.colorPalette.length],
        backgroundColor: 'rgba(0,0,0,0)',
        tension: 0.2,
        fill: false
      };
    });

    this.chartData = { labels, datasets };
  }
}