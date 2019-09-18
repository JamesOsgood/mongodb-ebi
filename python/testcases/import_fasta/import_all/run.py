# Import base test
import os
from datetime import datetime

from pymongo import MongoClient
from pymongo.mongo_client import MongoClient
from pysys.basetest import BaseTest

from FastaParser import FastaParser
import gzip

class PySysTest(BaseTest):

	def __init__ (self, descriptor, outsubdir, runner):
		BaseTest.__init__(self, descriptor, outsubdir, runner)

	def execute(self):
		parser = FastaParser(self)

		client = MongoClient(self.project.MONGODB_CONNECTION_STRING.replace('~', '='))
		db = client.ebi
		db.sequences.drop()
		
		# path = '/Users/james/data/EBI'
		path = self.input
		files = []
		self.getFilesToProcess(path, files, ['.gz'])

		for file in files:
			self.log.info(f'Importing {file}')
			docs = []
			with gzip.open(file, 'rt') as input_file:
				docs = parser.parseFile(input_file)
			db.sequences.insert_many(docs)
			self.log.info(f'Imported {len(docs)}')

	def getFilesToProcess(self, path, files, extensions):
		for item in os.listdir(path):
			fullpath = os.path.join(path, item)	
			if os.path.isdir(fullpath) and not item.startswith('.'):
				self.getFilesToProcess(fullpath, files, extensions)
			else:
				filename, fileext = os.path.splitext(fullpath)
				if fileext.lower() in extensions:
					# Make sure this folder is marked as backed up
					files.append(fullpath)

	def validate(self):
		pass
