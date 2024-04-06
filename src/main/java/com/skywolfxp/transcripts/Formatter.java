package com.skywolfxp.transcripts;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SkyWolfXP
 * Project: jda-html-transcripts
 */
public class Formatter
{
  
  private final static Pattern STRONG = Pattern.compile("\\*\\*(.+?)\\*\\*");
  private final static Pattern EM = Pattern.compile("\\*(.+?)\\*");
  private final static Pattern S = Pattern.compile("~~(.+?)~~");
  private final static Pattern U = Pattern.compile("__(.+?)__");
  private final static Pattern CODE_BLOCK = Pattern.compile("```(.+?)```");
  private final static Pattern CODE_LINE = Pattern.compile("`(.+?)`");
  // conver this /(?:\r\n|\r|\n)/g to patter in java
  private final static Pattern NEW_LINE = Pattern.compile("\\n");
  
  public static String formatBytes(long bytes)
  {
    int unit = 1024;
    if (bytes < unit)
    {return bytes + " B";}
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = "KMGTPE".charAt(exp - 1) + "";
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
  }
  
  public static String format(String originalText)
  {
    System.out.println(originalText);
    Matcher matcher = STRONG.matcher(originalText);
    String newText = originalText;
    while (matcher.find())
    {
      String group = matcher.group();
      newText = newText.replace(
              group,
              "<strong>" + group.replace("**", "") + "</strong>");
    }
    matcher = EM.matcher(newText);
    while (matcher.find())
    {
      String group = matcher.group();
      newText = newText.replace(
              group,
              "<em>" + group.replace("*", "") + "</em>");
    }
    matcher = S.matcher(newText);
    while (matcher.find())
    {
      String group = matcher.group();
      newText = newText.replace(
              group,
              "<s>" + group.replace("~~", "") + "</s>");
    }
    matcher = U.matcher(newText);
    while (matcher.find())
    {
      String group = matcher.group();
      newText = newText.replace(
              group,
              "<u>" + group.replace("__", "") + "</u>");
    }
    matcher = CODE_BLOCK.matcher(newText);
    boolean findCode = false;
    while (matcher.find())
    {
      String group = matcher.group();
      newText = newText.replace(
              group,
              "<div class=\"pre pre--multiline nohighlight\">"
                      + group.replace("```", "").substring(3, -3) + "</div>");
      findCode = true;
    }
    if (!findCode)
    {
      matcher = CODE_LINE.matcher(newText);
      while (matcher.find())
      {
        String group = matcher.group();
        newText = newText.replace(
                group,
                "<span class=\"pre pre--inline\">" + group.replace("`", "") + "</span>");
      }
    }
    matcher = NEW_LINE.matcher(newText);
    while (matcher.find())
    {
      newText = newText.replace(matcher.group(), "<br />");
    }
    return newText;
  }
  
  public static String toHex(Color color)
  {
    String hex = Integer.toHexString(color.getRGB() & 0xffffff);
    while (hex.length() < 6)
    {
      hex = "0%s".formatted(hex);
    }
    return hex;
  }
}