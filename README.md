# TechFatec BI — Módulo de Relatórios (Padrão Bridge)

Expansão do módulo de relatórios do sistema de inteligência de negócios da TechFatec.
O sistema legado só gerava o **Relatório de Vendas** em **PDF**. Este projeto refatora
o módulo com o **Design Pattern Bridge** para que:

- qualquer **tipo de relatório** (Vendas, RH, e futuros) possa ser exportado para
- qualquer **formato** (PDF, Excel/XLSX, HTML, e futuros)

sem explosão de subclasses (`RelatorioVendasPDF`, `RelatorioVendasExcel`,
`RelatorioRHPDF`, `RelatorioRHExcel`...) e respeitando o **Princípio Aberto/Fechado (OCP)**
do SOLID: o código existente não é alterado para adicionar um novo relatório
ou um novo formato — apenas se estende com uma nova classe.

## Por que Bridge e não Herança simples?

Se a variação "tipo de relatório × formato de exportação" fosse resolvida só com
herança, cada combinação nova exigiria uma nova classe (crescimento **multiplicativo**:
2 relatórios × 3 formatos = 6 classes; 3 relatórios × 4 formatos = 12 classes...).

O Bridge separa as duas dimensões em **duas hierarquias independentes** e as conecta
por agregação (a "ponte"): a classe `Relatorio` guarda uma referência a um
`FormatoExportacao` e delega a ele a etapa de exportação, em vez de implementá-la.
Crescimento passa a ser **aditivo**: uma nova classe por relatório, uma nova classe
por formato.

## Diagrama de classes

![Diagrama de classes do padrão Bridge aplicado ao módulo de relatórios](docs/diagrama-classes.png)

O diagrama separa o problema em duas hierarquias independentes, ligadas por agregação —
a essência do padrão Bridge. Do lado da **abstração**, a classe abstrata `Relatorio`
mantém uma referência protegida ao atributo `exportador`, do tipo `FormatoExportacao`,
e declara o método abstrato `gerarRelatorio()`. As subclasses `RelatorioVendas` e
`RelatorioRH` herdam dessa abstração e implementam sua própria lógica de conteúdo, sem
qualquer conhecimento do formato final de saída. Do lado da **implementação**, a
interface `FormatoExportacao` define o contrato comum (`desenharCabecalho`,
`desenharCorpo` e `finalizarArquivo`), realizado pelas classes concretas
`ExportadorPDF`, `ExportadorExcel` e `ExportadorHTML`.

- **Abstraction**: `Relatorio` (abstrata) → guarda a referência ao `FormatoExportacao`.
- **Refined Abstractions**: `RelatorioVendas`, `RelatorioRH`.
- **Implementor**: `FormatoExportacao` (interface).
- **Concrete Implementors**: `ExportadorPDF`, `ExportadorExcel`, `ExportadorHTML`.
- **Client**: `Main`, o único ponto do sistema onde há `new` de classes concretas.

## Fluxo de execução (sequência)

![Diagrama de sequência: instanciação, injeção de dependência e delegação](docs/diagrama-sequencia.png)

O diagrama de sequência ilustra o fluxo de execução de uma chamada típica ao sistema.
A classe cliente `Main` primeiro instancia um exportador concreto (`new ExportadorPDF()`)
e, em seguida, cria a abstração `RelatorioVendas`, injetando essa instância via
construtor — é exatamente nesse passo que a "ponte" entre as duas hierarquias é
estabelecida em tempo de execução. Ao chamar `gerarRelatorio()`, o objeto `relatorio`
não implementa a formatação diretamente: ele delega cada etapa do processo —
`desenharCabecalho(titulo)`, `desenharCorpo(dados)` e `finalizarArquivo()` — ao objeto
`exportador` recebido por injeção de dependência. Essa delegação demonstra o benefício
central do padrão: se fosse necessário gerar o mesmo relatório em Excel ou HTML,
bastaria injetar uma instância diferente (`ExportadorExcel` ou `ExportadorHTML`) —
nenhuma linha de código em `Relatorio`, `RelatorioVendas` ou `RelatorioRH` precisaria
ser alterada.

## Estrutura de diretórios

```
techfatec-relatorios/
├── docs/
│   ├── diagrama-classes.png
│   └── diagrama-sequencia.png
├── src/
│   ├── abstracao/          # Hierarquia de Relatórios (Abstraction)
│   │   ├── Relatorio.java
│   │   ├── RelatorioVendas.java
│   │   └── RelatorioRH.java
│   ├── implementacao/      # Hierarquia de Exportadores (Implementor)
│   │   ├── FormatoExportacao.java
│   │   ├── ExportadorPDF.java
│   │   ├── ExportadorExcel.java
│   │   └── ExportadorHTML.java
│   └── cliente/            # Script de validação
│       └── Main.java
└── README.md
```

## Injeção de dependência

Nenhuma classe de `abstracao/` faz `new` de um exportador concreto. O construtor
de `Relatorio` (e das subclasses) **recebe** um `FormatoExportacao` já pronto:

```java
protected Relatorio(FormatoExportacao exportador) {
    this.exportador = exportador;
}
```

A troca de formato em tempo de execução é feita pelo método `setExportador`,
sem recriar o objeto `Relatorio` e sem que ele saiba qual classe concreta está
por trás da interface:

```java
relatorioVendas.setExportador(new ExportadorExcel());
```

Toda instanciação concreta (`new ExportadorPDF()`, `new RelatorioVendas(...)` etc.)
acontece **apenas** em `cliente/Main.java`.

## Como compilar e executar

```bash
# a partir da raiz do projeto
javac -d bin src/implementacao/*.java src/abstracao/*.java src/cliente/*.java
java -cp bin cliente.Main
```

### Saída esperada (resumo)

1. `Relatorio de Vendas` exportado em **PDF** (`desenharCabecalho` → `desenharCorpo` → `finalizarArquivo`).
2. O **mesmo objeto** `relatorioVendas`, com o exportador trocado em runtime,
   exportado em **Excel (XLSX)**.
3. `Relatorio de Desempenho de RH` exportado em **HTML**.

## Extensibilidade (OCP na prática)

- **Novo formato** (ex.: CSV): criar `ExportadorCSV implements FormatoExportacao`.
  Nenhuma classe de `abstracao/` precisa ser tocada.
- **Novo relatório** (ex.: Relatório Financeiro): criar
  `RelatorioFinanceiro extends Relatorio`. Nenhuma classe de `implementacao/`
  precisa ser tocada.
- Em nenhum dos dois casos há alteração de código já existente — apenas adição
  de classes novas, exatamente como pede o Princípio Aberto/Fechado.
