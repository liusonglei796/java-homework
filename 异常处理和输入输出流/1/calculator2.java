package step1;

public class Calculator2 {

    // 获得产品单价：返回 float，增加 float 哑元参数
    public float getValue(String type, float dummy) throws Exception {
        if (type.equals("cookie")) {
            return 1.11f;
        } else {
            return 5.5f;
        }
    }

    // 获得产品数量：返回 int，增加 int 哑元参数
    public int getValue(String type, int dummy) throws Exception {
        if (type.equals("cookie")) {
            return 10;
        } else {
            return 20;
        }
    }

    public float calculate() throws Exception {
        float price = getValue("cookie", 0.0f);
        int amount = getValue("cookie", 0);
        return price * amount;
    }
}