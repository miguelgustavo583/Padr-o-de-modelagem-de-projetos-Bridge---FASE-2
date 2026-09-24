package implementacao;


public interface IExportador {

    void exportar(String nomeRelatorio, String conteudo);
    String getFormato();
}
