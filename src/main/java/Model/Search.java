package Model;

public class Search {
    private String keyWords;
    private String name;
    private String category;

    public Integer getSearchID() {
        return searchID;
    }
    public void setSearchID(Integer searchID) {
        this.searchID = searchID;
    }
    private Integer searchID;

    public String getKeyWords() {
        return keyWords;
    }
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
