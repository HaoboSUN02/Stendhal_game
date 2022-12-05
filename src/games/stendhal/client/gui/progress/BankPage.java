package games.stendhal.client.gui.progress;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
//import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

//import games.stendhal.client.gui.j2d.BackgroundPainter;
import games.stendhal.client.gui.layout.SBoxLayout;
import games.stendhal.client.gui.layout.SLayout;
import games.stendhal.client.gui.textformat.HtmlPreprocessor;
//import games.stendhal.server.entity.player.*;
//import marauroa.common.game.RPObject;
//import marauroa.common.game.RPSlot;
//import games.stendhal.server.entity.slot.*;
//import java.util.ArrayList;


public class BankPage extends JComponent implements HyperlinkListener {
		private static final int INDEX_WIDTH = 180;
		/** Image used for the log background. */
		//private static final String BACKGROUND_IMAGE = "data/gui/scroll_background.png";
		/** Html area for the subjects. */
		private final JEditorPane indexArea;
		/** The html area. */
		private final JEditorPane contentArea;
		/** Scrolling component of the index area. */
		private final JScrollPane indexScrollPane;
		/** Scrolling component of the content html area. */
		private final JScrollPane contentScrollPane;

		/** Query that is used to update the index area. */
		private ProgressStatusQuery indexQuery;
		/** Additional data for the index updating query. */
		private String indexQueryData;

		/** Query that is used to update the content area. */
		private ProgressStatusQuery contentQuery;
		/** Additional data for the content updating query. */
		private String contentQueryData;
		/** Name of the font. */
		private String fontName;

		

		
		/**
		 * Create a new page.
		 */
		public BankPage() {
			this.setLayout(new SBoxLayout(SBoxLayout.VERTICAL));
			JComponent panels = SBoxLayout.createContainer(SBoxLayout.HORIZONTAL, SBoxLayout.COMMON_PADDING);
			add(panels, SBoxLayout.constraint(SLayout.EXPAND_X,
					SLayout.EXPAND_Y));

			indexArea = new ProgressLog.PrettyEditorPane();
			indexArea.addHyperlinkListener(this);

			indexScrollPane = new JScrollPane(indexArea);
			// Fixed width
			indexScrollPane.setMaximumSize(new Dimension(INDEX_WIDTH, Integer.MAX_VALUE));
			indexScrollPane.setMinimumSize(new Dimension(INDEX_WIDTH, 0));
			// Turn off caret following
			Caret caret = indexArea.getCaret();
			if (caret instanceof DefaultCaret) {
				((DefaultCaret) caret).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
			}

			panels.add(indexScrollPane, SLayout.EXPAND_Y);

			contentArea = new ProgressLog.PrettyEditorPane();
			// Does not need a listener. There should be no links

			contentScrollPane = new JScrollPane(contentArea);
			panels.add(contentScrollPane, SBoxLayout.constraint(SLayout.EXPAND_X,
					SLayout.EXPAND_Y));

			JComponent buttonBox = SBoxLayout.createContainer(SBoxLayout.HORIZONTAL, SBoxLayout.COMMON_PADDING);
			buttonBox.setAlignmentX(RIGHT_ALIGNMENT);
			buttonBox.setBorder(BorderFactory.createEmptyBorder(SBoxLayout.COMMON_PADDING,
					0, SBoxLayout.COMMON_PADDING, SBoxLayout.COMMON_PADDING));
			add(buttonBox);
			// A button for reloading the page contents
			JButton refresh = new JButton("Update");
			refresh.setMnemonic(KeyEvent.VK_U);
			refresh.setAlignmentX(Component.RIGHT_ALIGNMENT);
			refresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					update();
				}
			});
			buttonBox.add(refresh);
			JButton closeButton = new JButton("Close");
			closeButton.setMnemonic(KeyEvent.VK_C);
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ProgressLog bankLog = new ProgressLog("");
					bankLog.getWindow().dispose();
				}
			});
			buttonBox.add(closeButton);
		}

	
		
		/**
		 * Update the page from the latest data from the server.
		 */
		void update() {
			if (indexQuery != null) {
				indexQuery.fire(indexQueryData);
			}
			if (contentQuery != null && (contentQueryData != null)) {
				contentQuery.fire(contentQueryData);
			}
		}

		/**
		 * Set the font.
		 *
		 * @param font font name
		 */
		void setFontName(String font) {
			fontName = font;
			updateOnFontChange();
		}

		@Override
		public void setFont(Font font) {
			super.setFont(font);
			// The font itself is not used, but the size is
			updateOnFontChange();
		}

		/**
		 * Update only if visible to avoid opening the window just because
		 * the font setting changed.
		 */
		private void updateOnFontChange() {
			Container top = this.getTopLevelAncestor();
			if (top != null && top.isVisible()) {
				update();
			}
		}

		/**
		 * Set the subject index.
		 *
		 * @param slots list of slots available on this page

		 */
		
		//Index for bank statement
		public void setBankIndex(List<String> slots) {
			/*
			 * Order the slot alphabetically. The server provides them ordered
			 * by internal name (and does not really guarantee even that), not
			 * by the human readable name.
			 */
			Collections.sort(slots);
			StringBuilder text = new StringBuilder("<html>");
			text.append(createStyleDefinition());
			for (String elem : slots) {
				text.append("<p>");
					text.append("<a href=\"");
					text.append(elem);
					text.append("\">");
					text.append(elem);
					text.append("</a>");
				}
			text.append("</html>");
			
			indexArea.setText(text.toString());
		}

		/**
		 * StyleSheet for the scroll html areas. Margins are needed to avoid
		 * drawing over the scroll borders.
		 *
		 * @return style sheet
		 */
		private String createStyleDefinition() {
			int fontSize = getFont().getSize() + 2;
			return "<style type=\"text/css\">body {font-family:" + fontName
					+ "; font-size:" + fontSize
					+ "; margin:12px} p {margin:4px 0px} a {color:#a00000} li, ul {margin-left:10px}</style>";
		}

		/**
		 * Set the page contents. Each of the content strings is shown as its
		 * own paragraph.
		 *
		 * @param header page header
		 * @param description description of the quest
		 * @param information information
		 * @param contents content paragraphs
		 * @param repeatable <code>true</code> if the quest should be marked
		 * 	repeatable, otherwise <code>false</code>
		 */
		public void setContent(String header, String description, String information,
				List<String> contents) {
			StringBuilder text = new StringBuilder("<html>");
			text.append(createStyleDefinition());

			// header
			if (header != null) {
				text.append("<h2>");
				text.append(header); 
				text.append("</h2>");
			}

			// information
			if ((information != null) && (!information.trim().equals(""))) {
				text.append("<p style=\"font-family:arial; color: #FF0000\"><b>");
				text.append(information);
				text.append("</b></p>");
			}

			// description
			if (description != null) {
				text.append("<p><i>");
				text.append(description);
				text.append("</i></p>");
			}

			// details
			HtmlPreprocessor preprocessor = new HtmlPreprocessor();
			if (!contents.isEmpty()) {
				text.append("<ul>");
				for (String elem : contents) {
					text.append("<li>");
					text.append(preprocessor.preprocess(elem));
					text.append("</li>");
				}
				text.append("</ul>");
			}
			text.append("</html>");
			contentArea.setText(text.toString());

			/*
			 * Scroll to top. This needs to be pushed to the even queue, because
			 * otherwise the scroll event triggered by changing the text would run
			 * after this.
			 */
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					contentScrollPane.getVerticalScrollBar().setValue(0);
				}
			});
		}

		@Override
		public void hyperlinkUpdate(HyperlinkEvent event) {
			if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				/*
				 * It would be more correct to read the parameter from the link
				 * target, but swing does not give access to that when it fails
				 * to parse it as an URL.
				 */
				contentQueryData = event.getDescription();
				if (contentQuery != null) {
					contentQuery.fire(contentQueryData);
				}
			}
		}
	}

		
	
	 



		
	
	 


