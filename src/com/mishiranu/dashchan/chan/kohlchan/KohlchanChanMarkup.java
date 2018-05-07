package com.mishiranu.dashchan.chan.kohlchan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Pair;

import chan.content.ChanMarkup;
import chan.text.CommentEditor;

public class KohlchanChanMarkup extends ChanMarkup
{
	private static final int SUPPORTED_TAGS = TAG_BOLD | TAG_ITALIC | TAG_UNDERLINE | TAG_STRIKE | TAG_SPOILER
			| TAG_CODE;

	public KohlchanChanMarkup()
	{
        addTag("strong", TAG_BOLD);
        addTag("em", TAG_ITALIC);
		addTag("span", "quote", TAG_QUOTE);
        addTag("span", "spoiler", TAG_SPOILER);
        addTag("code", TAG_CODE);
        addTag("span", "style", "text-decoration: underline", TAG_UNDERLINE);
        addTag("span", "style", "text-decoration: line-through", TAG_STRIKE);
        addPreformatted("code", true);
	}

	@Override
	public CommentEditor obtainCommentEditor(String boardName)
	{
	    // kohlchan Tags
		CommentEditor commentEditor = new CommentEditor.BulletinBoardCodeCommentEditor();
		commentEditor.addTag(TAG_BOLD, "[b]", "[/b]");
		commentEditor.addTag(TAG_ITALIC, "[i]", "[/i]");
        commentEditor.addTag(TAG_UNDERLINE, "[u]", "[/u]");
        commentEditor.addTag(TAG_STRIKE, "[s]", "[/s]");
        commentEditor.addTag(TAG_SPOILER, "[spoiler]", "[/spoiler]");
        commentEditor.addTag(TAG_CODE, "[code]", "[/code]");
        commentEditor.addTag(TAG_QUOTE, ">", "");
		return commentEditor;
	}

	@Override
	public boolean isTagSupported(String boardName, int tag)
	{
		return (SUPPORTED_TAGS & tag) == tag;
	}

	private static final Pattern THREAD_LINK = Pattern.compile("(\\d+).html(?:#(\\d+))?$");

	@Override
	public Pair<String, String> obtainPostLinkThreadPostNumbers(String uriString)
	{
		Matcher matcher = THREAD_LINK.matcher(uriString);
		if (matcher.find()) return new Pair<>(matcher.group(1), matcher.group(2));
		return null;
	}
}