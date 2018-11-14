package sonjb.phillit.ime_counter.common;

public class Word_Hit {
    private String num;
    private String Word;
    private String hit;

    public void add(String num, String word, String hit){
        this.num = num;
        this.Word = word;
        this.hit = hit;
    }

    public String getNum(){
        return num;
    }
    public String getWord(){
        return Word;
    }
    public String gethit(){
        return hit;
    }
}
