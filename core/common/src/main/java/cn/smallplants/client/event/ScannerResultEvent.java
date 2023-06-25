package cn.smallplants.client.event;

public class ScannerResultEvent {
    private final String result;

    public ScannerResultEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
