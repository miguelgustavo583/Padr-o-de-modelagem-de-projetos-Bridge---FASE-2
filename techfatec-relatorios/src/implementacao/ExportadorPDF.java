package implementacao;

public class ExportadorPDF implements IExportador {

    @Override
    public void exportar(String nomeRelatorio, String conteudo) {
        System.out.println("----------------------------------------------------");
        System.out.println("[ExportadorPDF] Gerando arquivo .pdf");
        System.out.println("Relatorio: " + nomeRelatorio);
        System.out.println("Conteudo : " + conteudo);
        System.out.println("Status   : " + nomeRelatorio.replace(" ", "_") + ".pdf gerado com sucesso.");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public String getFormato() {
        return "PDF";
    }
}
