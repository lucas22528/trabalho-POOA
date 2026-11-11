package modelo;

public enum StatusPedido {
	 CRIADO, 
	 PAGO, 
	 EM_PREPARO, 
	 ENVIADO, 
	 ENTREGUE;

    public boolean isFinal() {
        return this == ENTREGUE;
    }

   
    public StatusPedido proximo() {
        switch (this) {
            case CRIADO:
                return PAGO;
            case PAGO:
                return EM_PREPARO;
            case EM_PREPARO:
                return ENVIADO;
            default:
                return ENTREGUE;
        }
    }
}
