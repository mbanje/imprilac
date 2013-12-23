package paquetImprilac;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

public class FileUploadbean {
	
	private ArrayList<File> files = new ArrayList<File>();
	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public int getSize() {
	    if (getFiles().size()>0){
	        return getFiles().size();
	    }else 
	    {
	        return 0;
	    }
	}

	public void paint(OutputStream stream, Object object) throws IOException {
	    stream.write(getFiles().get((Integer)object).getData());
	}
	
public void act()
{
	}
	
public void listener(UploadEvent event) throws Exception{
	
    UploadItem item = event.getUploadItem();
    File file = new File();
    file.setLength(item.getData().length);
    file.setName(item.getFileName());
    file.setData(item.getData());
    files.add(file);
    
System.out.println("Les fichiers sur files sont au nombre de "+this.getSize());
} 

}
