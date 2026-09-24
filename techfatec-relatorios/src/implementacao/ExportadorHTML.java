package implementacao;

public class ExportadorHTML implements IExportador {

    @Override
    public void exportar(String nomeRelatorio, String conteudo) {
        System.out.println("----------------------------------------------------");
        System.out.println("[ExportadorHTML] Gerando arquivo .html");
        System.out.println("Relatorio: " + nomeRelatorio);
        System.out.println("Conteudo : <html><body>" + conteudo + "</body></html>");
        System.out.println("Status   : " + nomeRelatorio.replace(" ", "_") + ".html gerado com sucesso.");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public String getFormato() {
        return "HTML";
    }
}
