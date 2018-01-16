package wct.main;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        File file = new File("/home/xuanlinhha/WCT/output/1.mp4");
        List listOfFiles = new ArrayList();
        listOfFiles.add(file);

        FileTransferable ft = new FileTransferable(listOfFiles);

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ft, new ClipboardOwner() {
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("Lost ownership");
            }
        });
    }

    public static class FileTransferable implements Transferable {

        private List listOfFiles;

        public FileTransferable(List listOfFiles) {
            this.listOfFiles = listOfFiles;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.javaFileListFlavor.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            return listOfFiles;
        }
    }

}
/**
 *
 * @author xuanlinhha
 */
//public class Test {
//
//    public static void main(String[] args) {
//        FileSelection fs = new FileSelection();
//        List<File> selectedFiles = new ArrayList<File>();
//        File f1 = new File("/home/xuanlinhha/WCT/output/1_会理法#院偷偷开#庭#判#法##轮##功学#员 律#师谴责违#法.mp4");
//        selectedFiles.add(f1);
//        fs.setFiles(selectedFiles);
//        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fs, fs);
//    }
//
//}
