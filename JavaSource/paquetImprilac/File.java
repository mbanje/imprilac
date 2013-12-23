package paquetImprilac;

class File {
	
    private String Name;
    private String path;
    private long length;
    private byte[] data;
    
    
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
    
    

}
