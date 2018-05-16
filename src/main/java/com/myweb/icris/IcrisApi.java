package com.myweb.icris;

import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class IcrisApi {
    private HttpClientUtil httpClientUtil = null;

    public IcrisApi() throws Exception {
        httpClientUtil = new HttpClientUtil();
    }

    public void agree() throws Exception {
        EntityUtils.toString(httpClientUtil.agree().getEntity(), "UTF-8");
    }

    public void english() throws Exception {
        EntityUtils.toString(httpClientUtil.changelocate("https://www.icris.cr.gov.hk/csci/change_locale_i.do?language=en&country=US").getEntity(), "UTF-8");
    }

    public void chinese() throws Exception {
        EntityUtils.toString(httpClientUtil.changelocate("https://www.icris.cr.gov.hk/csci/change_locale_i.do?language=zh&country=CN").getEntity(), "UTF-8");
    }

    public String searchByNo(int no) throws Exception {
        return EntityUtils.toString(httpClientUtil.searchCompany(no).getEntity(), "UTF-8");
    }

    public String searchDocumentPage(String no) throws Exception {
        return EntityUtils.toString(httpClientUtil.searchDocmentPage(no).getEntity(), "UTF-8");
    }

    public String searchDocument(String no, int page, int select) throws Exception {
        return EntityUtils.toString(httpClientUtil.searchDocment(no, page, select).getEntity(), "UTF-8");
    }

    public boolean parseCompany(String result) {
        Document document = Jsoup.parse(result);
        Elements form = document.select("form[name=docsFilterSearch]").select("table");
        if (form.isEmpty()) {
            return false;
        }
        Elements company = form.first().select("tr");
        company.forEach(e-> System.out.println(e.text()));
        Elements history = form.last().select("tr");
        history.forEach(e -> System.out.println(e.text()));
        return true;
    }

    public int parseDocumentPage(String result) {
        Document document = Jsoup.parse(result);
        Elements form = document.select("form[name=cmm_order_type]").select("table");
        if (form.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(form.last().select("option").last().text());
    }

    public boolean parseDocument(String result) {
        Document document = Jsoup.parse(result);
        Elements tds = document.select("form[name=cmm_order_type]").select("table[dwcopytype=CopyTableRow]").select("tr[bgcolor=#CCCCCC]");
        if (tds.isEmpty()) {
            return false;
        }
        tds.forEach(e -> System.out.println(e.text()));
        return true;
    }


    public static void main(String[] args) throws Exception {
        IcrisApi api = new IcrisApi();
        api.agree();
        api.chinese();
        api.parseCompany(api.searchByNo(156489));
        int page = api.parseDocumentPage(api.searchDocumentPage("0156489"));
        for (int i = 1; i <= page; i++) {
            api.parseDocument(api.searchDocument("0156489", i, page));
        }
    }
}
