<div align="center">

# 📊 Padrao-De-Modelagem — Módulo de Relatórios

### Padrão de Projeto **Bridge**

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](#)
[![Design Pattern](https://img.shields.io/badge/Design%20Pattern-Bridge-blueviolet?style=for-the-badge)](#)
[![SOLID](https://img.shields.io/badge/SOLID-OCP-2ea44f?style=for-the-badge)](#)

*Expansão do módulo de relatórios do sistema de inteligência de negócios da TechFatec.*

</div>

---

## 🧩 Visão Geral

O sistema legado só gerava o **Relatório de Vendas** em **PDF**. Este projeto refatora o módulo com o **Design Pattern Bridge**, permitindo que:

| Dimensão | Exemplos | Pode crescer sem alterar a outra? |
|---|---|:---:|
| 📁 **Tipo de relatório** | Vendas, RH, *futuros* | ✅ |
| 📤 **Formato de exportação** | PDF, Excel (XLSX), HTML, *futuros* | ✅ |

Tudo isso **sem explosão de subclasses** (`RelatorioVendasPDF`, `RelatorioVendasExcel`, `RelatorioRHPDF`, `RelatorioRHExcel`...) e respeitando o **Princípio Aberto/Fechado (OCP)** do SOLID.

---

## 👥 Dupla

| Integrante |
|---|
| Henrique de Moraes Rodrigues |
| Miguel Gustavo de Sousa Campos |

---

## 🤔 Por que Bridge e não Herança simples?

> Se a variação **"tipo de relatório × formato de exportação"** fosse resolvida só com herança, cada combinação nova exigiria uma nova classe — um crescimento explosivo e difícil de manter.

O **Bridge** separa essas duas dimensões em **duas hierarquias independentes** e as conecta por **agregação**: a classe `Relatorio` guarda uma referência a um `FormatoExportacao` e delega a ele a etapa de exportação.

---

## 🗺️ Diagrama de Classes

<div align="center">

![Diagrama de classes do padrão Bridge aplicado ao módulo de relatórios](techfatec-relatorios/diagramas/diagrama-classe.png)

*O diagrama separa o problema em duas hierarquias independentes, ligadas por agregação — a essência do padrão Bridge.*

</div>

| Papel no padrão | Classe(s) |
|---|---|
| 🔷 **Abstraction** | `Relatorio` (abstrata) → guarda a referência ao `FormatoExportacao` |
| 🔹 **Refined Abstractions** | `RelatorioVendas`, `RelatorioRH` |
| 🔶 **Implementor** | `FormatoExportacao` (interface) |
| 🔸 **Concrete Implementors** | `ExportadorPDF`, `ExportadorExcel`, `ExportadorHTML` |
| 👤 **Client** | `Main` |

---

## 🔄 Fluxo de Execução — Diagrama de Sequência

<div align="center">

![Diagrama de sequência: instanciação, injeção de dependência e delegação](techfatec-relatorios/diagramas/diagrama-sequencia.png)

*Instanciação do exportador, criação do relatório com injeção de dependência e delegação das etapas de exportação.*

</div>

O diagrama de sequência mostra a instanciação do exportador, a criação do relatório com injeção de dependência e a delegação das etapas de exportação (`desenharCabecalho`, `desenharCorpo` e `finalizarArquivo`).

> 💡 **Principal vantagem:** o mesmo relatório pode utilizar diferentes formatos de exportação **sem alterar** as classes da hierarquia de relatórios.

---

## 📂 Estrutura de Diretórios

```text
README.md
└── techfatec-relatorios/
    ├── diagramas/
    │   ├── diagrama-classe.png
    │   └── diagrama-sequencia.png
    └── src/
        ├── abstracao/
        │   ├── Relatorio.java
        │   ├── RelatorioVendas.java
        │   └── RelatorioRH.java
        ├── implementacao/
        │   ├── IExportador.java
        │   ├── ExportadorPDF.java
        │   ├── ExportadorExcel.java
        │   └── ExportadorHTML.java
        └── cliente/
            └── Main.java
```

---

## 💉 Injeção de Dependência

Nenhuma classe de `abstracao/` faz `new` de um exportador concreto. O construtor de `Relatorio` recebe um `FormatoExportacao` já pronto:

```java
protected Relatorio(FormatoExportacao exportador) {
    this.exportador = exportador;
}
```

A troca de formato em **tempo de execução** pode ser feita com:

```java
relatorioVendas.setExportador(new ExportadorExcel());
```

> 🏭 Toda instanciação concreta acontece no `cliente/Main.java`.

---

## ▶️ Como Compilar e Executar

```bash
javac -d bin src/implementacao/*.java src/abstracao/*.java src/cliente/*.java
java -cp bin cliente.Main
```

---

## ✅ Saída Esperada

1. 🧾 `Relatorio de Vendas` exportado em **PDF**.
2. 🔁 O mesmo objeto `relatorioVendas` com o exportador trocado em runtime e exportado em **Excel (XLSX)**.
3. 🌐 `Relatorio de Desempenho de RH` exportado em **HTML**.

---

## 🧱 Extensibilidade — OCP na Prática

| Quero adicionar... | O que fazer | Classes existentes são alteradas? |
|---|---|:---:|
| 🆕 Novo **formato** | Criar `ExportadorCSV implements FormatoExportacao` | ❌ Não |
| 🆕 Novo **relatório** | Criar `RelatorioFinanceiro extends Relatorio` | ❌ Não |

As duas extensões podem ser adicionadas **sem modificar** as classes existentes das outras hierarquias — o coração do Princípio Aberto/Fechado. 🎯

<div align="center">

---

*Projeto acadêmico — ModelagemProjeto*

</div>
