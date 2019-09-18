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

		client = MongoClient(self.project.MONGODB_CONNECTION_STRING.replace('~', '='))
		db = client.ebi
		pipeline = [
			{'$limit' : 1000},
			{ '$project' : { 'sequence_id' : 1, 'meta' : 1, 'description' : 1, 'seq' : '$seq.seq'}},
			{ '$out' : 'sequence_output'}
			]
		
		db.sequences.aggregate(pipeline)
	def validate(self):
		pass
