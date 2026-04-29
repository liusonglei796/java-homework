package step1;

public class Calculator1 {

    public float getPrice(String type) throws Exception {
        // 获得产品单价
        if (type.equals("cookie")) {
            return 1.11f;
        } else {
            return 5.5f;
        }
    }

    public int getAmount(String type) throws Exception {
        // 获得产品数量
        if (type.equals("cookie")) {
            return 10;
        } else {
            return 20;
        }
    }

    public float calculate() throws Exception {
        float price = getPrice("cookie");
        int amount = getAmount("cookie");
        return price * amount;
    }
}