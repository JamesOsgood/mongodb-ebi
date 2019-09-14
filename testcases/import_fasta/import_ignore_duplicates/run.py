# Import base test
import os
from datetime import datetime

from pymongo import MongoClient, UpdateOne
from pymongo.errors import BulkWriteError
from pysys.basetest import BaseTest

from FastaParser import FastaParser

class PySysTest(BaseTest):

	def __init__ (self, descriptor, outsubdir, runner):
		BaseTest.__init__(self, descriptor, outsubdir, runner)

	def execute(self):
		parser = FastaParser(self)

		client = MongoClient(self.project.MONGODB_CONNECTION_STRING.replace('~', '='))
		db = client.ebi
		db.sequences_sample.drop()

		files = [ 'contig.fa', 'contig2.fa']
		for file in files:
			self.log.info(f'Parsing file {file}')
			input_file = os.path.join(self.input, file)
			doc = None
			with open(input_file) as f:
				docs = parser.parseFile(input_file)
			
			ops = []
			for doc in docs:
				# Create an upsert
				hash = doc['seq']['hash']
				filter = {'seq.hash' : hash}
				update = {'$inc' : { 'dup_count' : 1 },
						  '$setOnInsert' : doc}
				self.log.info(f'Updating {hash}')
				ret = db.sequences_sample.update(filter, update, upsert=True)
				self.log.info(self.formatResults(ret))
			self.log.info('\n')
		
	def validate(self):
		pass

	def formatResults(self, ret):
		del ret['opTime']
		del ret['$clusterTime']
		del ret['electionId']
		del ret['operationTime']
		return ret