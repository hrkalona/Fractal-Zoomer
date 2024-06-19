
package fractalzoomer.gui;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author hrkalona2
 */
class ListItemTransferHandler extends TransferHandler {
	private static final long serialVersionUID = -8102218396379743071L;
	
	public ListItemTransferHandler() {
        super();
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new StringSelection(exportString(c));
    }

    @Override
    public boolean canImport(TransferSupport info) {
        
        return info.isDrop() && info.isDataFlavorSupported(DataFlavor.stringFlavor);
        
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE; //TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferSupport info) {
        if (!canImport(info)) {
            return false;
        }

        try {
            String data = (String) info.getTransferable().getTransferData(DataFlavor.stringFlavor);
            return importString((JList) info.getComponent(), data, info);
        } catch (UnsupportedFlavorException | IOException ufe) {}
        
        return false;
    }

    //Take the incoming string and wherever there is a
    //newline, break it into a separate item in the list.
    protected boolean importString(JComponent c, String str, TransferSupport info) {
        JList target = (JList) c;
        DefaultListModel listModel = (DefaultListModel) target.getModel();

        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
        int index = dl.getIndex();

        int max = listModel.getSize();
        if (index < 0 || index > max) {
            index = max;
        }

        //check if indices are consecutive 
        boolean isConsecutive = true;
        for (int i = 0; i < indices.length - 1; i++) {
            if (indices[i] + 1 != indices[i + 1]) {
                isConsecutive = false;
                break;
            }
        }

        if (isConsecutive) { //check if the drop index is the last index + 1
            if (indices[indices.length - 1] + 1 == index) {
                return false;
            }

            //check if the drop index exists in the selected indices
            for (int i = 0; i < indices.length; i++) {
                if (index == indices[i]) {
                    return false;
                }
            }
        }

        addIndex = index;

        String[] values = str.split("\n");
        addCount = values.length;
        for (String value : values) {
            int idx = index++;
            listModel.add(idx, value);
            target.addSelectionInterval(idx, idx);
        }

        return true;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
        cleanup(c, action == MOVE);
    }

    private void cleanup(JComponent c, boolean remove) {
        if (remove && indices != null) {
            JList source = (JList) c;
            DefaultListModel model = (DefaultListModel) source.getModel();
            if (addCount > 0) {
                for (int i = 0; i < indices.length; i++) {
                    if (indices[i] >= addIndex) {
                        indices[i] += addCount;
                    }
                }
            }
            for (int i = indices.length - 1; i >= 0; i--) {
                model.remove(indices[i]);
            }
        }
        indices = null;
        addCount = 0;
        addIndex = -1;
    }

    //Bundle up the selected items in the list
    //as a single string, for export.
    protected String exportString(JComponent c) {
        JList list = (JList) c;
        indices = list.getSelectedIndices();
        List<String> values = list.getSelectedValuesList();

        String buf = "";
        
        for (int i = 0; i < values.size(); i++) {
            buf += values.get(i);
            if (i != values.size() - 1) {
                buf += "\n";
            }
        }

        return buf;
    }

    private int[] indices = null;
    private int addIndex = -1; //Location where items were added
    private int addCount = 0;  //Number of items added.
}
