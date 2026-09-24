package cliente;

import abstracao.Relatorio;
import abstracao.RelatorioVendas;
import abstracao.RelatorioRH;
import implementacao.IExportador;
import implementacao.ExportadorPDF;
import implementacao.ExportadorExcel;
import implementacao.ExportadorHTML;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== TechFatec BI - Modulo de Relatorios (Padrao Bridge) ===\n");

        IExportador exportadorPDF = new ExportadorPDF();
        Relatorio relatorioVendas = new RelatorioVendas(exportadorPDF, 152340.75, 318);

        System.out.println(">> Passo 1: Relatorio de Vendas, formato inicial = " + exportadorPDF.getFormato());
        relatorioVendas.gerarRelatorio();

        IExportador exportadorExcel = new ExportadorExcel();
        relatorioVendas.setExportador(exportadorExcel);

        System.out.println("\n>> Passo 2: MESMO objeto de Relatorio de Vendas, "
                + "formato alterado em runtime para = " + exportadorExcel.getFormato());
        relatorioVendas.gerarRelatorio();

        IExportador exportadorHTML = new ExportadorHTML();
        Relatorio relatorioRH = new RelatorioRH(exportadorHTML, 214, 87.5);

        System.out.println("\n>> Passo 3: Relatorio de Desempenho de RH, formato = " + exportadorHTML.getFormato());
        relatorioRH.gerarRelatorio();

        System.out.println("\n=== Fim da validacao: mesma hierarquia de Relatorio, "
                + "3 formatos diferentes, sem nenhum 'if/else' de formato e sem subclasses combinadas. ===");
    }
}
