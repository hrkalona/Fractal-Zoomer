/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 *
 * @author hrkalona2
 */
class ListItemTransferHandler extends TransferHandler {
  private final DataFlavor localObjectFlavor;
  private Object[] transferedObjects = null;
  public ListItemTransferHandler() {
    localObjectFlavor = new ActivationDataFlavor(
      Object[].class, DataFlavor.javaJVMLocalObjectMimeType, "Array of items");
  }
  @SuppressWarnings("deprecation")
  @Override protected Transferable createTransferable(JComponent c) {
    JList list = (JList) c;
    indices = list.getSelectedIndices();
    transferedObjects = list.getSelectedValues();
    return new DataHandler(transferedObjects, localObjectFlavor.getMimeType());
  }
  @Override public boolean canImport(TransferSupport info) {
    if(!info.isDrop() || !info.isDataFlavorSupported(localObjectFlavor)) {
      return false;
    }
    return true;
  }
  @Override public int getSourceActions(JComponent c) {
    return MOVE; //TransferHandler.COPY_OR_MOVE;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean importData(TransferSupport info) {
    if(!canImport(info)) {
      return false;
    }
    JList target = (JList)info.getComponent();
    JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
    DefaultListModel listModel = (DefaultListModel)target.getModel();
    int index = dl.getIndex();
    int max = listModel.getSize();
    if(index<0 || index>max) {
      index = max;
    }
    addIndex = index;
    try {
      Object[] values = (Object[])info.getTransferable().getTransferData(
          localObjectFlavor);
      addCount = values.length;
      for(int i=0; i<values.length; i++) {
        int idx = index++;
        listModel.add(idx, values[i]);
        target.addSelectionInterval(idx, idx);
      }
      return true;
    } catch(UnsupportedFlavorException ufe) {
      ufe.printStackTrace();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
    return false;
  }
  @Override protected void exportDone(
      JComponent c, Transferable data, int action) {
    cleanup(c, action == MOVE);
  }
  private void cleanup(JComponent c, boolean remove) {
    if(remove && indices != null) {
      JList source = (JList)c;
      DefaultListModel model = (DefaultListModel)source.getModel();
      if(addCount > 0) {
        //http://java-swing-tips.googlecode.com/svn/trunk/DnDReorderList/src/java/example/MainPanel.java
        for(int i=0; i<indices.length; i++) {
          if(indices[i]>=addIndex) {
            indices[i] += addCount;
          }
        }
      }
      for(int i=indices.length-1; i>=0; i--) {
        model.remove(indices[i]);
      }
    }
    indices  = null;
    addCount = 0;
    addIndex = -1;
  }
  private int[] indices = null;
  private int addIndex  = -1; //Location where items were added
  private int addCount  = 0;  //Number of items added.
}
