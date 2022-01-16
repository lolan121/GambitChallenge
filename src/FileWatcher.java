import java.io.File;
import java.util.*;

public abstract class FileWatcher extends TimerTask {
    private long timeStamp;
    private File file;

    public FileWatcher(File file){
        this.file = file;
        this.timeStamp = file.lastModified();
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public final void run(){
        long timeStamp = file.lastModified();

        if(this.timeStamp != timeStamp){
            this.timeStamp = timeStamp;
            onFileChange();
        }
    }

    abstract void onFileChange();

}
