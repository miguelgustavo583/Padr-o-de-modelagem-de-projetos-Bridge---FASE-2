package abstracao;

import implementacao.IExportador;

public class RelatorioVendas extends Relatorio {

    private final double totalVendido;
    private final int quantidadePedidos;

    public RelatorioVendas(IExportador exportador, double totalVendido, int quantidadePedidos) {
        super(exportador);
        this.totalVendido = totalVendido;
        this.quantidadePedidos = quantidadePedidos;
    }

    @Override
    protected String gerarConteudo() {
        return String.format(
            "Total vendido: R$ %.2f | Pedidos: %d",
            totalVendido, quantidadePedidos
        );
    }

    @Override
    public String getNome() {
        return "Relatorio de Vendas";
    }
}
