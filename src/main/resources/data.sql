insert into SITE(title, description, url) values('Noticias Las Flores', 'Diario digital ciudad de Las Flores, Buenos Aires, Argentina', 'https://www.noticiaslasflores.com.ar/');

SET @LABEL_LOCALES_ID = 1;
insert into LABEL(id, title) values(@LABEL_LOCALES_ID, 'Locales');
SET @LABEL_DEPORTES_ID = 2;
insert into LABEL(id, title) values(@LABEL_DEPORTES_ID, 'Deportes');
SET @LABEL_CULTURA_ID = 3;
insert into LABEL(id, title) values(@LABEL_CULTURA_ID, 'Cultura');
SET @LABEL_EDUCACION_ID = 4;
insert into LABEL(id, title) values(@LABEL_EDUCACION_ID, 'Educacion');
SET @LABEL_POLITICA_ID = 5;
insert into LABEL(id, title) values(@LABEL_POLITICA_ID, 'Politica');
SET @LABEL_SALUD_ID = 6;
insert into LABEL(id, title) values(@LABEL_SALUD_ID, 'Salud');
SET @LABEL_POLICIALES_ID = 7;
insert into LABEL(id, title) values(@LABEL_POLICIALES_ID, 'Policiales');
SET @LABEL_PROVINCIALES_ID = 8;
insert into LABEL(id, title) values(@LABEL_PROVINCIALES_ID, 'Provinciales');
SET @LABEL_INSTITUCIONES_ID = 9;
insert into LABEL(id, title) values(@LABEL_INSTITUCIONES_ID, 'Instituciones');
SET @LABEL_NACIONALES_ID = 10;
insert into LABEL(id, title) values(@LABEL_NACIONALES_ID, 'Nacionales');
SET @LABEL_REGIONALES_ID = 11;
insert into LABEL(id, title) values(@LABEL_REGIONALES_ID, 'Regionales');
SET @LABEL_MUNDO_ID = 12;
insert into LABEL(id, title) values(@LABEL_MUNDO_ID, 'Mundo');
SET @LABEL_ENTREVISTAS_ID = 13;
insert into LABEL(id, title) values(@LABEL_ENTREVISTAS_ID, 'Entrevista');
SET @LABEL_CURIOSIDADES_ID = 14;
insert into LABEL(id, title) values(@LABEL_CURIOSIDADES_ID, 'Entrevista');

SET @SCRAP_NLF_LFD_ID = 1;
insert into SCRAP_RULE(id, headline, title, link, image) values(
@SCRAP_NLF_LFD_ID,
'$input.getElementsByTag("article")',
'$input.getElementsByTag("h2").first().getElementsByTag("a").first().textNodes().get(0).text()',
'$input.getElementsByTag("h2").first().getElementsByTag("a").first().attr("href")',
'($input.getElementsByTag("img").first()!=null) ? $input.getElementsByTag("img").first().attr("src") : ""');

SET @SCRAP_HLF_ID = 2;
 insert into SCRAP_RULE(id, headline, title, link, image) values(
 @SCRAP_HLF_ID,
'$input.getElementsByTag("article")',
'$input.getElementsByTag("h1").first().getElementsByTag("a").first().textNodes().get(0).text()',
'$input.getElementsByTag("h1").first().getElementsByTag("a").first().attr("href")',
'($input.getElementsByTag("img").first()!=null) ? $input.getElementsByTag("img").first().attr("data-src") : ""');

SET @SCRAP_PR_ID = 3;
insert into SCRAP_RULE(id, headline, title, link, image) values(
 @SCRAP_PR_ID,
'$input.getElementsByClass("td_module_wrap")',
'$input.getElementsByTag("h3").first().getElementsByTag("a").first().text().intern()',
'$input.getElementsByTag("h3").first().getElementsByTag("a").first().attr("href")',
'$input.getElementsByClass("td_module_wrap").first().getElementsByClass("entry-thumb").attr("data-img-url")');

insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Nacional', 'https://www.noticiaslasflores.com.ar/categoria/nacional/', 1, @LABEL_NACIONALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Provincial', 'https://www.noticiaslasflores.com.ar/categoria/provincial/', 1, @LABEL_PROVINCIALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('La ciudad', 'https://www.noticiaslasflores.com.ar/categoria/la-ciudad/', 1,  @LABEL_LOCALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Politica', 'https://www.noticiaslasflores.com.ar/categoria/politica/', 1, @LABEL_PROVINCIALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Policiales', 'https://www.noticiaslasflores.com.ar/categoria/policiales/', 1, @LABEL_POLICIALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Deportes', 'https://www.noticiaslasflores.com.ar/categoria/deportes/', 1, @LABEL_DEPORTES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Cultura', 'https://www.noticiaslasflores.com.ar/categoria/cultura/', 1, @LABEL_CULTURA_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Salud', 'https://www.noticiaslasflores.com.ar/categoria/salud/', 1, @LABEL_SALUD_ID, @SCRAP_NLF_LFD_ID);

insert into SITE(title, description, url) values('Las Flores Digital', 'Diario digital ciudad de Las Flores, Buenos Aires, Argentina', 'http://lasfloresdigital.com.ar/sitio/');

insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('La Ciudad', 'http://lasfloresdigital.com.ar/sitio/category/la-ciudad/', 2, @LABEL_LOCALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('La region', 'http://lasfloresdigital.com.ar/sitio/category/la-region/', 2, @LABEL_REGIONALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Provinciales', 'http://lasfloresdigital.com.ar/sitio/category/provinciales/', 2, @LABEL_PROVINCIALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Nacionales', 'http://lasfloresdigital.com.ar/sitio/category/nacionales/', 2, @LABEL_NACIONALES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Deportes', 'http://lasfloresdigital.com.ar/sitio/category/deportes/', 2, @LABEL_DEPORTES_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('El Mundo', 'http://lasfloresdigital.com.ar/sitio/category/el-mundo/', 2, @LABEL_MUNDO_ID, @SCRAP_NLF_LFD_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Policiales', 'http://lasfloresdigital.com.ar/sitio/category/policiales/', 2, @LABEL_POLICIALES_ID, @SCRAP_NLF_LFD_ID);

insert into SITE(title, description, url) values('Ahora Las Flores', 'Ahora Las Flores, Buenos Aires, Argentina', 'https://ahoralasflores.com.ar');

insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Locales', 'https://ahoralasflores.com.ar/categoria/4/locales', 3, @LABEL_LOCALES_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Provinciales', 'https://ahoralasflores.com.ar/categoria/3/provinciales', 3, @LABEL_PROVINCIALES_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Nacionales', 'https://ahoralasflores.com.ar/categoria/1/nacionales', 3, @LABEL_NACIONALES_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Deporte', 'https://ahoralasflores.com.ar/categoria/2/deporte', 3, @LABEL_DEPORTES_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Entrevistas', 'https://ahoralasflores.com.ar/categoria/9/entrevistas', 3, @LABEL_ENTREVISTAS_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Cultura y Educación', 'https://ahoralasflores.com.ar/categoria/5/cultura-y-educacion', 3, @LABEL_CULTURA_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Curiosidades', 'https://ahoralasflores.com.ar/categoria/7/curiosidades', 3, @LABEL_CURIOSIDADES_ID, @SCRAP_HLF_ID);

insert into SITE(title, description, url) values('Play Radios', 'Diario digital ciudad de Las Flores, Buenos Aires, Argentina', 'http://playradios.com.ar');

insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Locales', 'http://playradios.com.ar/category/locales/', 4, @LABEL_LOCALES_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Deportes', 'http://playradios.com.ar/category/locales/deportes/', 4, @LABEL_DEPORTES_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Cultura', 'http://playradios.com.ar/category/cultura/', 4, @LABEL_CULTURA_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Educacion', 'http://playradios.com.ar/category/locales/educacion-locales/', 4, @LABEL_EDUCACION_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Politica', 'http://playradios.com.ar/category/locales/politica-locales/', 4, @LABEL_POLITICA_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Salud', 'http://playradios.com.ar/category/locales/salud/', 4, @LABEL_SALUD_ID, @SCRAP_HLF_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Policiales', 'http://playradios.com.ar/category/policiales/', 4, @LABEL_POLICIALES_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Provinciales', 'http://playradios.com.ar/category/provinciales/', 4, @LABEL_PROVINCIALES_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Instituciones', 'http://playradios.com.ar/category/locales/instituciones/', 4, @LABEL_INSTITUCIONES_ID, @SCRAP_PR_ID);
insert into CATEGORY(title, url, site_id, label_id, scrap_rule_id) values('Nacionales', 'http://playradios.com.ar/category/nacionales/', 4, @LABEL_NACIONALES_ID, @SCRAP_PR_ID);
