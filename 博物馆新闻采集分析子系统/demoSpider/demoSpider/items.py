# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class DemospiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    id = scrapy.Field()
    content = scrapy.Field()
    tf_idf_keywords = scrapy.Field()
    tf_idf_score = scrapy.Field()
    TextRank_keywords = scrapy.Field()
    TextRank_score = scrapy.Field()