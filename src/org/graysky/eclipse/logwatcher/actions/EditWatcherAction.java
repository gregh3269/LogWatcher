package org.graysky.eclipse.logwatcher.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.graysky.eclipse.logwatcher.NewWatcherDialog;
import org.graysky.eclipse.logwatcher.views.LogWatcherView;
import org.graysky.eclipse.logwatcher.views.WatcherEntry;
import org.graysky.eclipse.util.ImageUtils;

/**
 * Edits the currently active Watcher.
 */
public class EditWatcherAction extends Action
{
	private LogWatcherView	m_view = null;
	private static ImageDescriptor IMAGE_DESC = null; 
	
	public EditWatcherAction(LogWatcherView p)
	{
		m_view = p;
		
		setText("Edit Watcher");
		setToolTipText("Edit this watcher");
		setImageDescriptor(IMAGE_DESC);
	}
	
	public void run() {
         WatcherEntry entry = m_view.getSelectedEntry();
         if (entry != null) {
			 int topIndex = entry.getViewer().getTopIndex();
			 int caret = entry.getViewer().getTextWidget().getCaretOffset();
             NewWatcherDialog d = new NewWatcherDialog(m_view.getFolder().getShell(), true);
             d.setFilters(entry.getFilters());
             d.setInterval(entry.getWatcher().getInterval());
             d.setNumLines(entry.getWatcher().getNumLines());
             d.setFile(new File(entry.getWatcher().getFilename()));
             if (d.open() == Window.OK) {
                 m_view.editWatcher(entry,d.getInterval(), d.getNumLines(), d.getFilters());
                 entry.getViewer().refresh();
				 entry.getViewer().setTopIndex(topIndex);
				 entry.getViewer().getTextWidget().setCaretOffset(caret);	
             }
         }
     }
	
	static {
		IMAGE_DESC = ImageUtils.createImageDescriptor("icons/edit.gif");
	}
}