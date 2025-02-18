# 📊 Relatório de Tempo de Execução dos Testes no RabbitMQ

## 📌 Objetivo
Mensurar a **quantidade máxima de requisições por segundo** e comparar os tempos de execução utilizando diferentes **configurações de filas e mensagens** no RabbitMQ.

## 📌 Metodologia
Para garantir a precisão dos resultados, cada teste foi executado **mais de uma vez**. Observamos que o **primeiro teste realizado em cada configuração foi mais lento**, possivelmente devido a fatores como:
- **Inicialização do RabbitMQ**
- **Cache do sistema operacional**
- **Otimizações internas do RabbitMQ e do Java**

Por esse motivo, os tempos registrados **correspondem às execuções subsequentes**, quando o sistema já estava em um estado mais estável.

Cada teste consistiu no envio de **1 milhão de mensagens**, sendo **registrado o tempo total** para a primeira mensagem e a milionésima mensagem.

---

## 📌 Resultados dos Testes

| **Tipo de Fila e Mensagem** | **Tempo Mensagem 1** | **Tempo Mensagem 1.000.000** | **Tempo Total** |
|-----------------------------|----------------------|------------------------------|-----------------|
| **Fila Não Durável + Mensagem Não Persistente** | **9 ms** | **194.891 ms** (~3 min 15 s) | **194.891 ms** |
| **Fila Durável + Mensagem Não Persistente** | **16 ms** | **203.579 ms** (~3 min 23 s) | **203.579 ms** |
| **Fila Durável + Mensagem Persistente** | **25 ms** | **228.226 ms** (~3 min 48 s) | **228.226 ms** |

---

## 📌 Análise dos Resultados
1. **O teste com a fila não durável e mensagem não persistente foi o mais rápido**, com um tempo total de **~3min 15s**.
2. **A fila durável adicionou um pequeno overhead**, aumentando o tempo para **~3min 23s**.
3. **A fila durável com mensagens persistentes foi a mais lenta**, chegando a **~3min 48s**, devido ao custo adicional de gravação em disco.

Obs: Tive que rodar os testes algumas vezes para evitar problemas de c

---

## 📌 Conclusão
- **Se a prioridade for desempenho**, utilizar **fila não durável e mensagens não persistentes** é a melhor opção.  
- **Se for necessário garantir que as mensagens não sejam perdidas**, a melhor alternativa é **fila durável + mensagens persistentes**, apesar do aumento no tempo de processamento.  
- **A diferença de tempo entre os testes confirma que a gravação no disco impacta diretamente a velocidade do RabbitMQ**.   

---

📌 **Autor:** Oliviada Costa Oliva 
📅 **Data:** 18/02/2025
🚀 **Sistema de Mensageria:** RabbitMQ  
