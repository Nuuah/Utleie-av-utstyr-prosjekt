package bacit.web.bacit_model;

public class FileModel {

    private String name;
    private byte[] contents;
    private String contentType;

    public FileModel(String name, byte[] contents, String contentType) {
        this.name = name;
        this.contents = contents;
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
