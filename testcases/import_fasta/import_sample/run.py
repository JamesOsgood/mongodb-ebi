# Import base test
import os
from datetime import datetime

from pymongo import MongoClient
from pymongo.mongo_client import MongoClient
from pysys.basetest import BaseTest

from FastaParser import FastaParser


class PySysTest(BaseTest):

	def __init__ (self, descriptor, outsubdir, runner):
		BaseTest.__init__(self, descriptor, outsubdir, runner)

	def execute(self):
		parser = FastaParser(self)

		input_file = os.path.join(self.input, 'contig.fa')
		doc = None
		with open(input_file) as f:
			docs = parser.parseFile(input_file)
		
		client = MongoClient(self.project.MONGODB_CONNECTION_STRING.replace('~', '='))
		db = client.ebi
		db.sequences_sample.drop()

		db.sequences_sample.insert_many(docs)
		
	def validate(self):
		pass
