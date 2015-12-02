package imprimir;


public class OrderItem {

    char[] temp = new char[]{' '};

    public OrderItem(char delimit) {
        temp = new char[]{delimit};
    }

    public String GetItemCantidad(String orderItem) {
        String[] delimitado = orderItem.split("" + temp);
        return delimitado[0];
    }

    public String GetItemNombre(String orderItem) {
        String[] delimitado = orderItem.split("" + temp);
        return delimitado[1];
    }

    public String GetItemPrecio(String orderItem) {
        String[] delimitado = orderItem.split("" + temp);
        return delimitado[2];
    }

    public String GeneraItem(String cantidad, String nombre, String precio) {
        return cantidad + temp[0] + nombre + temp[0] + precio;
    }
}
