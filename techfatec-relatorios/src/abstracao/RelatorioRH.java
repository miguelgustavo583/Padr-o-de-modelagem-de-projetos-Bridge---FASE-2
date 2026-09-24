package abstracao;

import implementacao.IExportador;

public class RelatorioRH extends Relatorio {

    private final int totalFuncionarios;
    private final double indiceSatisfacao;

    public RelatorioRH(IExportador exportador, int totalFuncionarios, double indiceSatisfacao) {
        super(exportador);
        this.totalFuncionarios = totalFuncionarios;
        this.indiceSatisfacao = indiceSatisfacao;
    }

    @Override
    protected String gerarConteudo() {
        return String.format(
            "Funcionarios ativos: %d | Indice de satisfacao: %.1f%%",
            totalFuncionarios, indiceSatisfacao
        );
    }

    @Override
    public String getNome() {
        return "Relatorio de Desempenho de RH";
    }
}
