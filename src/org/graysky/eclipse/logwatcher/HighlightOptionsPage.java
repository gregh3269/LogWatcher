package org.graysky.eclipse.logwatcher;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.graysky.eclipse.logwatcher.filters.Filter;
import org.graysky.eclipse.logwatcher.filters.HighlightAction;

public class HighlightOptionsPage extends WizardPage
{
	private Text		m_filterText;
	private Composite	m_actionOptions;
	private Color		m_color 			= null;
	private Color		m_startingColor		= null;
	private Combo		m_actionsCombo;

	/**
	 * Constructor for HighlightOptionsPage.
	 * @param pageName
	 */
	public HighlightOptionsPage(String pageName)
	{
		super(pageName);
	}

	/**
	 * Constructor for HighlightOptionsPage.
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public HighlightOptionsPage(String pageName, String title, ImageDescriptor titleImage)
	{
		super(pageName, title, titleImage);
	}

	public void createControl(Composite parent) 
	{
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
	
		m_startingColor = new Color(getShell().getDisplay(), 0, 0, 0);
		
		GridData gridData;
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 20;
		composite.setLayout(layout);
	
		new Label(composite, SWT.NONE).setText("Highlight color:");
		
		final Canvas c = new Canvas(composite, SWT.NONE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.heightHint = 20;
		gridData.widthHint = 20;
		c.setLayoutData(gridData);
		c.setBackground(m_startingColor);
		
		Button colorButton = new Button(composite, SWT.PUSH);
		colorButton.setText("Select...");
		colorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt)
			{
				ColorDialog dialog = new ColorDialog(getShell());
				dialog.open();
				
				if (dialog.getRGB() != null) {
					if (m_color != null) {
						m_color.dispose();	
					}
					
					m_color = new Color(getShell().getDisplay(), dialog.getRGB());
					c.setBackground(m_color);
					setPageComplete(true);
				}
			}
		});
		
		setPageComplete(false);
	}

	public String getTitle()
	{
		return "Set the highlight color.";
	}

	public IWizardPage getNextPage()
	{
		return null;
	}

    public void dispose()
    {
        super.dispose();
        m_startingColor.dispose();
    }

    public Color getColor()
    {
        return m_color;
    }

}