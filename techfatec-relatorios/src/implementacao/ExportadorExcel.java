package implementacao;

public class ExportadorExcel implements IExportador {

    @Override
    public void exportar(String nomeRelatorio, String conteudo) {
        System.out.println("----------------------------------------------------");
        System.out.println("[ExportadorExcel] Gerando arquivo .xlsx");
        System.out.println("Relatorio: " + nomeRelatorio);
        System.out.println("Conteudo : " + conteudo);
        System.out.println("Status   : " + nomeRelatorio.replace(" ", "_") + ".xlsx gerado com sucesso.");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public String getFormato() {
        return "Excel (XLSX)";
    }
}
