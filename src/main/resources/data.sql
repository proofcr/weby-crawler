insert into site(title, description, url, enabled, scrap_thumb_enabled)
values('Noticias Las Flores', 'Diario digital ciudad de Las Flores, Buenos Aires, Argentina',
'https://www.noticiaslasflores.com.ar/', true, true);

SET @LABEL_LOCALES_ID = 1;
insert into label(id, title) values(@LABEL_LOCALES_ID, 'Locales');
SET @LABEL_DEPORTES_ID = 2;
insert into label(id, title) values(@LABEL_DEPORTES_ID, 'Deportes');
SET @LABEL_CULTURA_ID = 3;
insert into label(id, title) values(@LABEL_CULTURA_ID, 'Cultura');
SET @LABEL_EDUCACION_ID = 4;
insert into label(id, title) values(@LABEL_EDUCACION_ID, 'Educacion');
SET @LABEL_POLITICA_ID = 5;
insert into label(id, title) values(@LABEL_POLITICA_ID, 'Politica');
SET @LABEL_SALUD_ID = 6;
insert into label(id, title) values(@LABEL_SALUD_ID, 'Salud');
SET @LABEL_POLICIALES_ID = 7;
insert into label(id, title) values(@LABEL_POLICIALES_ID, 'Policiales');
SET @LABEL_PROVINCIALES_ID = 8;
insert into label(id, title) values(@LABEL_PROVINCIALES_ID, 'Provinciales');
SET @LABEL_INSTITUCIONES_ID = 9;
insert into label(id, title) values(@LABEL_INSTITUCIONES_ID, 'Instituciones');
SET @LABEL_NACIONALES_ID = 10;
insert into label(id, title) values(@LABEL_NACIONALES_ID, 'Nacionales');
SET @LABEL_REGIONALES_ID = 11;
insert into label(id, title) values(@LABEL_REGIONALES_ID, 'Regionales');
SET @LABEL_MUNDO_ID = 12;
insert into label(id, title) values(@LABEL_MUNDO_ID, 'Mundo');
SET @LABEL_ENTREVISTAS_ID = 13;
insert into label(id, title) values(@LABEL_ENTREVISTAS_ID, 'Entrevista');
SET @LABEL_CURIOSIDADES_ID = 14;
insert into label(id, title) values(@LABEL_CURIOSIDADES_ID, 'Curiosidades');

SET @SCRAP_NLF_LFD_ID = 1;
insert into scrap_rule(id, headline, title, link, image) values(
@SCRAP_NLF_LFD_ID,
'$input.getElementsByTag("article")',
'$input.getElementsByTag("h2").first().getElementsByTag("a").first().textNodes().get(0).text()',
'$input.getElementsByTag("h2").first().getElementsByTag("a").first().attr("href")',
'($input.getElementsByTag("img").first()!=null) ? $input.getElementsByTag("img").first().attr("src") : ""');

SET @SCRAP_HLF_ID = 2;
 insert into scrap_rule(id, headline, title, link, image) values(
 @SCRAP_HLF_ID,
'$input.getElementsByTag("article")',
'$input.getElementsByTag("h1").first().getElementsByTag("a").first().textNodes().get(0).text()',
'$input.getElementsByTag("h1").first().getElementsByTag("a").first().attr("href")',
'($input.getElementsByTag("img").first()!=null) ? $input.getElementsByTag("img").first().attr("data-src") : ""');

SET @SCRAP_PR_ID = 3;
insert into scrap_rule(id, headline, title, link, image) values(
 @SCRAP_PR_ID,
'$input.getElementsByClass("td_module_wrap")',
'$input.getElementsByTag("h3").first().getElementsByTag("a").first().text().intern()',
'$input.getElementsByTag("h3").first().getElementsByTag("a").first().attr("href")',
'$input.getElementsByClass("td_module_wrap").first().getElementsByClass("entry-thumb").attr("data-img-url")');

insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Nacional', 'https://www.noticiaslasflores.com.ar/categoria/nacional/', true, 1, @LABEL_NACIONALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Provincial', 'https://www.noticiaslasflores.com.ar/categoria/provincial/', true, 1, @LABEL_PROVINCIALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('La ciudad', 'https://www.noticiaslasflores.com.ar/categoria/la-ciudad/', true, 1,  @LABEL_LOCALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Politica', 'https://www.noticiaslasflores.com.ar/categoria/politica/', true, 1, @LABEL_PROVINCIALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Policiales', 'https://www.noticiaslasflores.com.ar/categoria/policiales/', true, 1, @LABEL_POLICIALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Deportes', 'https://www.noticiaslasflores.com.ar/categoria/deportes/', true, 1, @LABEL_DEPORTES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Cultura', 'https://www.noticiaslasflores.com.ar/categoria/cultura/', true, 1, @LABEL_CULTURA_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Salud', 'https://www.noticiaslasflores.com.ar/categoria/salud/', true, 1, @LABEL_SALUD_ID, @SCRAP_NLF_LFD_ID);

insert into site(title, description, url, enabled, scrap_thumb_enabled) values('Las Flores Digital', 'Diario digital ciudad de Las Flores, Buenos Aires, Argentina',
'http://lasfloresdigital.com.ar/sitio/', true, true);

insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('La Ciudad', 'http://lasfloresdigital.com.ar/sitio/category/la-ciudad/', true, 2, @LABEL_LOCALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('La region', 'http://lasfloresdigital.com.ar/sitio/category/la-region/', true, 2, @LABEL_REGIONALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Provinciales', 'http://lasfloresdigital.com.ar/sitio/category/provinciales/', true, 2, @LABEL_PROVINCIALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Nacionales', 'http://lasfloresdigital.com.ar/sitio/category/nacionales/', true, 2, @LABEL_NACIONALES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Deportes', 'http://lasfloresdigital.com.ar/sitio/category/deportes/', true, 2, @LABEL_DEPORTES_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('El Mundo', 'http://lasfloresdigital.com.ar/sitio/category/el-mundo/', true, 2, @LABEL_MUNDO_ID, @SCRAP_NLF_LFD_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Policiales', 'http://lasfloresdigital.com.ar/sitio/category/policiales/', true, 2, @LABEL_POLICIALES_ID, @SCRAP_NLF_LFD_ID);

insert into site(title, description, url, enabled, scrap_thumb_enabled) values('Ahora Las Flores', 'Ahora Las Flores, Buenos Aires, Argentina',
'https://ahoralasflores.com.ar', true, true);

insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Locales', 'https://ahoralasflores.com.ar/categoria/4/locales', true, 3, @LABEL_LOCALES_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Provinciales', 'https://ahoralasflores.com.ar/categoria/3/provinciales', true, 3, @LABEL_PROVINCIALES_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Nacionales', 'https://ahoralasflores.com.ar/categoria/1/nacionales', true, 3, @LABEL_NACIONALES_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Deporte', 'https://ahoralasflores.com.ar/categoria/2/deporte', true, 3, @LABEL_DEPORTES_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Entrevistas', 'https://ahoralasflores.com.ar/categoria/9/entrevistas', true, 3, @LABEL_ENTREVISTAS_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Cultura y Educación', 'https://ahoralasflores.com.ar/categoria/5/cultura-y-educacion', true, 3, @LABEL_CULTURA_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Curiosidades', 'https://ahoralasflores.com.ar/categoria/7/curiosidades', true, 3, @LABEL_CURIOSIDADES_ID, @SCRAP_HLF_ID);

insert into site(title, description, url, enabled, scrap_thumb_enabled) values('Play Radios', 'Diario digital ciudad de Las Flores, Buenos Aires, Argentina',
'http://playradios.com.ar', true, true);

insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Locales', 'http://playradios.com.ar/category/locales/', true, 4, @LABEL_LOCALES_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Deportes', 'http://playradios.com.ar/category/locales/deportes/', true, 4, @LABEL_DEPORTES_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Cultura', 'http://playradios.com.ar/category/cultura/', true, 4, @LABEL_CULTURA_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Educacion', 'http://playradios.com.ar/category/locales/educacion-locales/', true, 4, @LABEL_EDUCACION_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Politica', 'http://playradios.com.ar/category/locales/politica-locales/', true, 4, @LABEL_POLITICA_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Salud', 'http://playradios.com.ar/category/locales/salud/', true, 4, @LABEL_SALUD_ID, @SCRAP_HLF_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Policiales', 'http://playradios.com.ar/category/policiales/', true, 4, @LABEL_POLICIALES_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Provinciales', 'http://playradios.com.ar/category/provinciales/', true, 4, @LABEL_PROVINCIALES_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Instituciones', 'http://playradios.com.ar/category/locales/instituciones/', true, 4, @LABEL_INSTITUCIONES_ID, @SCRAP_PR_ID);
insert into category(title, url, enabled, site_id, label_id, scrap_rule_id) values('Nacionales', 'http://playradios.com.ar/category/nacionales/', true, 4, @LABEL_NACIONALES_ID, @SCRAP_PR_ID);
