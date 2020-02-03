package hongvinhlcode.app.todolist.Main.Main.ToDo;

public class ToDo {
    private int id;
    private String title;
    private String des;
    private String remind;

    public ToDo(int id, String title, String des, String remind) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.remind = remind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }
}

