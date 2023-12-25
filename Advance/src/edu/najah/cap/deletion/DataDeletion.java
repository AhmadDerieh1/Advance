package edu.najah.cap.deletion;

public class DataDeletion {

    private static DataDeletion instance;

    private DataDeletion() {}

    private static DataDeletion getInstance() {
        if (instance == null) {
            synchronized (DataDeletion.class) {
                if (instance == null) {
                    instance = new DataDeletion();
                }
            }
        }
        return instance;
    }
    public void softDeleteData(String dataId) {}
    public void hardDeleteData(String dataId) {}

}
