package abstracao;

import implementacao.IExportador;

public abstract class Relatorio {

    protected IExportador exportador;

    protected Relatorio(IExportador exportador) {
        this.exportador = exportador;
    }

    public void setExportador(IExportador novoExportador) {
        this.exportador = novoExportador;
    }

    protected abstract String gerarConteudo();

    public abstract String getNome();

    public void gerarRelatorio() {
        String conteudo = gerarConteudo();
        exportador.exportar(getNome(), conteudo);
    }
}
