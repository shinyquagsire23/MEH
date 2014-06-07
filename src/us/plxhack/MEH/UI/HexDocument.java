package us.plxhack.MEH.UI;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author cpp-qt
 */
public class HexDocument extends PlainDocument
{

	private String text = "";

	@Override
	public void insertString(int offset, String txt, AttributeSet a)
	{
		try
		{
			text = getText(0, getLength());
			if ((text + txt).matches("[0-9a-fA-F]{0,8}"))
			{
				super.insertString(offset, txt, a);
			}
		}
		catch (Exception ex)
		{
			Logger.getLogger(HexDocument.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
